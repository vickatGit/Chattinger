package com.example.chattinger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chattinger.data.Models.MessageModel
import com.example.chattinger.data.Models.RoomModel
import com.example.chattinger.data.Models.UserModel

@Database(entities = [MessageModel::class,UserModel::class,RoomModel::class], version = 2)
abstract class ChatDb : RoomDatabase() {
    abstract fun getChatDao():ChatDAO
}