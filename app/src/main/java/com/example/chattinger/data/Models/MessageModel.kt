package com.example.chattinger.data.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageModel(@PrimaryKey(autoGenerate = true) val messageId:Int=0, val roomName:String, val messageContent:String, val userName:String,
                        var messageType:Int)
