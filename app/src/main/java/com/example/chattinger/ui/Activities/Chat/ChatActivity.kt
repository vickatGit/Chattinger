package com.example.chattinger.ui.Activities.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chattinger.R
import com.example.chattinger.Utils.Constants
import com.example.chattinger.data.Models.ChatInfoModel
import com.example.chattinger.data.Models.MessageModel
import com.example.chattinger.data.Models.RoomModel
import com.example.chattinger.ui.Activities.Adapters.ChatsAdapter
import com.example.chattinger.ui.ParentApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import javax.inject.Inject

class ChatActivity : AppCompatActivity() ,View.OnClickListener{

    lateinit var viewModel:ChatViewModel
    @Inject
    lateinit var viewModelFactory: ChatViewModelFactory
    lateinit var room:RoomModel
    lateinit var userName:String
    lateinit var mSocket:Socket
    var gson=Gson()
    lateinit var chatRecycler:RecyclerView
    private lateinit var RoomHeader:TextView
    lateinit var chatInput:EditText
    lateinit var sendMessage:FloatingActionButton
    lateinit var chatAdapter:ChatsAdapter
    var chats=ArrayList<MessageModel>(0)

    companion object{
        val TAG="Chat_Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        userName=intent.getStringExtra(Constants.GET_USER_NAME_TAG)!!
        room=intent.getParcelableExtra<RoomModel>(Constants.GET_Room_TAG)!!
        (application as ParentApplication).applicationComponent.inject(this)
        viewModel=ViewModelProvider(this,viewModelFactory).get(ChatViewModel::class.java)
        initialise()
        RoomHeader.text=room.roomName
        chatRecycler.layoutManager=LinearLayoutManager(this)
        chatAdapter= ChatsAdapter(chats,userName)
        chatRecycler.adapter=chatAdapter
        viewModel.getRoomChats(room.roomName).observe(this, Observer {
            Log.d(TAG, "onCreate: room chats $it")
            updateChat(it)
        })



        sendMessage.setOnClickListener(this)

        try {
            mSocket = IO.socket(Constants.SOCKET_PORT_URL)
            Log.d("success", "success to connect")

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect ${e}")
        }

        try {
            mSocket.connect()
        }catch(e:Exception){
            Log.d("fail", "Failed to connect to the server ${e}")
        }
        //Register all the listener and callbacks here.
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on("newUserToChatRoom", onNewUser) // To know if the new user entered the room.
        mSocket.on("updateChat", onUpdateChat) // To update if someone send a message to chatroom
        mSocket.on("userLeftChatRoom", onUserLeft)


    }
    var onConnect = Emitter.Listener {
        val data = ChatInfoModel(userName, room.roomName)
        val jsonData = gson.toJson(data) // Gson changes data object to Json type.
        mSocket.emit("subscribe", jsonData)
    }

    var onNewUser = Emitter.Listener {
        val name = it[0] as String //This pass the userName!
        val chat = MessageModel(userName = name, messageContent = "", roomName = room.roomName, messageType = Constants.USER_JOIN_VIEW_TYPE)
        addItemToRecyclerView(chat)
        Log.d(TAG, "on New User triggered.")
    }

    var onUserLeft = Emitter.Listener {
        val leftUserName = it[0] as String
        val chat: MessageModel = MessageModel(userName = leftUserName, roomName = "", messageContent = "", messageType = Constants.USER_LEAVE_VIEW_TYPE)
        addItemToRecyclerView(chat)
    }

    var onUpdateChat = Emitter.Listener {
        val chat: MessageModel = gson.fromJson(it[0].toString(), MessageModel::class.java)
        chat.messageType = Constants.CHAT_PARTNER_VIEW_TYPE
        Log.d("TAG", "recieve: incoming json is "+it)
        Log.d("TAG", "recieve: incoming "+chat)
        addItemToRecyclerView(chat)
        viewModel.insertMessage(chat)
    }


    private fun sendMessage() {
        val content = chatInput.text.toString()
        val sendData = MessageModel(userName = userName, messageContent = content, roomName = room.roomName, messageType = Constants.MY_MESSAGE_VIEW_TYPE)
        val jsonData = gson.toJson(sendData)
        mSocket.emit("newMessage", jsonData)


        val message = MessageModel(userName = userName, messageContent = content, roomName = room.roomName, messageType = Constants.MY_MESSAGE_VIEW_TYPE)
        addItemToRecyclerView(sendData)
        viewModel.insertMessage(message)
    }

    private fun addItemToRecyclerView(message: MessageModel) {
        runOnUiThread {
            chats.add(message)
            chatAdapter.notifyItemInserted(chats.size)
            chatInput.setText("")
            chatRecycler.scrollToPosition(chats.size - 1)
        }
    }



    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.send_message -> sendMessage()
//            R.id.leave -> onDestroy()
        }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.getRoomChats(room.roomName).observe(this, Observer {
            updateChat(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        val data = ChatInfoModel(userName, room.roomName)
        val jsonData = gson.toJson(data)

        mSocket.emit("unsubscribe", jsonData)
        mSocket.disconnect()
    }
    private fun initialise(){
        chatRecycler=findViewById(R.id.chats)
        sendMessage=findViewById(R.id.send_message)
        chatInput=findViewById(R.id.message_input)
        RoomHeader=findViewById(R.id.room_name)
    }
    private fun updateChat(it: ArrayList<MessageModel>) {
        Log.d(TAG, "updateChat: $chats")
        if(it.size != 0) {
            chats.clear()
            chats.addAll(it)
            chatAdapter.notifyDataSetChanged()
        }
    }
}