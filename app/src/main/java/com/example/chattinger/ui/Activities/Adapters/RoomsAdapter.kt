package com.example.chattinger.ui.Activities.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chattinger.R
import com.example.chattinger.Utils.Constants
import com.example.chattinger.data.Models.RoomModel
import com.example.chattinger.ui.Activities.Chat.ChatActivity
import com.example.chattinger.ui.Activities.RoomsActivity.RoomsActivity

class RoomsAdapter(
    val context: RoomsActivity,
    val userCreatedRooms: ArrayList<RoomModel>,
    val userName: String?
) : RecyclerView.Adapter<RoomsAdapter.RoomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.room_view,parent,false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.roomName.text=userCreatedRooms.get(position).roomName
        holder.itemView.setOnClickListener {
            val intent=Intent(context,ChatActivity::class.java)
            intent.putExtra(Constants.GET_Room_TAG,userCreatedRooms.get(position))
            intent.putExtra(Constants.GET_USER_NAME_TAG,userName)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userCreatedRooms.size
    }
    inner class RoomViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var roomName:TextView=itemView.findViewById(R.id.room_name)
    }
}