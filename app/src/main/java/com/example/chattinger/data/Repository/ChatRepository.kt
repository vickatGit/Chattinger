package com.example.chattinger.data.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chattinger.data.Models.MessageModel
import com.example.chattinger.data.Models.RoomModel
import com.example.chattinger.data.Models.UserModel
import com.example.chattinger.data.db.ChatDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(private val chatDb:ChatDb) {

    private var UsersList=MutableLiveData<ArrayList<UserModel>>()
    private var isInserted=MutableLiveData<Boolean>()
    private var userCreatedRooms=MutableLiveData<ArrayList<RoomModel>>()
    private var roomMessages=MutableLiveData<ArrayList<MessageModel>>()

    private var chatsReciever=MutableLiveData<List<MessageModel>>()
    fun getChats(): MutableLiveData<List<MessageModel>> {
        var chats=ArrayList<MessageModel>(0)
        CoroutineScope(Dispatchers.IO).launch {
            chats= chatDb.getChatDao().getAllMessages() as ArrayList<MessageModel>
            chatsReciever.postValue(chats)
        }
        chatsReciever.postValue(chats)
        return chatsReciever
    }

    fun loginUser(userName: String) {
        var isUserInserted:LiveData<Int>? = null
        CoroutineScope(Dispatchers.IO).launch {
            chatDb.getChatDao().loginUser(UserModel(userName = userName))
        }

    }

    fun getUsers(): MutableLiveData<ArrayList<UserModel>> {
        var users=ArrayList<UserModel>(0)
        CoroutineScope(Dispatchers.IO).launch {
            users= chatDb.getChatDao().getAllUsers() as ArrayList<UserModel>
            UsersList.postValue(users)

        }
        return UsersList
    }

    fun createRoom(nameOfRoom: String): MutableLiveData<Boolean> {
        CoroutineScope(Dispatchers.IO).launch {
            val iscreated=chatDb.getChatDao().createRoom(RoomModel(nameOfRoom))
            if(iscreated<0) {
                isInserted.postValue(false)
            }else{
                isInserted.postValue(true)
            }
            Log.d("TAG", "createRoom: getting All Rooms ${chatDb.getChatDao().getAllRooms()}")
        }
        return isInserted
    }

    fun getUserCreatedRooms(): MutableLiveData<ArrayList<RoomModel>> {
        var userRooms=ArrayList<RoomModel>(0)
        CoroutineScope(Dispatchers.IO).launch {
            userRooms= chatDb.getChatDao().getAllRooms() as ArrayList<RoomModel>
            userCreatedRooms.postValue(userRooms)
        }
        return userCreatedRooms
    }

    fun insertMessage(message: MessageModel) {
        CoroutineScope(Dispatchers.IO).launch { 
            chatDb.getChatDao().addMessage(message)
        }
    }
    fun getMessages(roomName:String): MutableLiveData<ArrayList<MessageModel>> {
        var roomChatsList=ArrayList<MessageModel>(1)
        CoroutineScope(Dispatchers.IO).launch {
            roomChatsList= chatDb.getChatDao().getChats(roomName) as ArrayList<MessageModel>
            Log.d("TAG", "getMessages: "+roomChatsList)
            roomMessages.postValue(roomChatsList)
        }
        return roomMessages
    }
}