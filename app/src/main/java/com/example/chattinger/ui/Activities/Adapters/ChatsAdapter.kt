package com.example.chattinger.ui.Activities.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chattinger.R
import com.example.chattinger.Utils.Constants
import com.example.chattinger.data.Models.MessageModel

class ChatsAdapter(val messages: List<MessageModel>, val userName: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        if(viewType==Constants.MY_MESSAGE_VIEW_TYPE){
            val view=inflater.inflate(R.layout.my_chat_view,parent,false)
            return outgoingMessageViewHolder(view)
        }
        else if(viewType==Constants.CHAT_PARTNER_VIEW_TYPE){
            val view=inflater.inflate(R.layout.others_chat_view,parent,false)
            return incomingMessageHolder(view)
        }else{
            val view=inflater.inflate(R.layout.user_join_leave_layout,parent,false)
            return joinLeaveLayout(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(messages.get(position).messageType==Constants.MY_MESSAGE_VIEW_TYPE) {
            val outholder:outgoingMessageViewHolder= holder as outgoingMessageViewHolder
            outholder.message.text=messages.get(position).messageContent
        }
        else if(messages.get(position).messageType==Constants.CHAT_PARTNER_VIEW_TYPE){
            val incHolder:incomingMessageHolder= holder as incomingMessageHolder
            Log.d("TAG", "onBindViewHolder: incoming "+messages.get(position).messageContent)
            incHolder.message.text=messages.get(position).messageContent
        }else{
            val incHolder:joinLeaveLayout= holder as joinLeaveLayout
            if(messages.get(position).messageType==Constants.USER_JOIN_VIEW_TYPE) {
                if(messages.get(position).userName==userName)
                    incHolder.message.text = "you've joined"
                else
                    incHolder.message.text = messages.get(position).userName + "  joined"
            }
            else
                incHolder.message.text=messages.get(position).userName+"  leaves"
        }
    }

    override fun getItemViewType(position: Int): Int {
        return (messages.get(position).messageType)
    }

    override fun getItemCount(): Int {
        return messages.size
    }
    class incomingMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val message: TextView =itemView.findViewById(R.id.inc_message)

    }
    class outgoingMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val message:TextView=itemView.findViewById(R.id.out_message)
    }
    class joinLeaveLayout(itemView: View) : RecyclerView.ViewHolder(itemView){
        val message:TextView=itemView.findViewById(R.id.message)
    }
}