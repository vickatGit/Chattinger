package com.example.chattinger.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.chattinger.data.Models.MessageModel
import com.example.chattinger.data.Models.RoomModel
import com.example.chattinger.data.Models.UserModel

@Dao
interface ChatDAO {
    @Insert
    fun addMessage(msg:MessageModel)

    @Query("SELECT * FROM MessageModel")
    fun getAllMessages():LiveData<List<MessageModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun loginUser(user:UserModel)

    @Query("SELECT * FROM UserModel")
    fun getAllUsers():List<UserModel>

    @Insert
    fun createRoom(room:RoomModel):Long

    @Query("SELECT * FROM RoomModel")
    fun getAllRooms():List<RoomModel>

    @Query("SELECT * FROM MessageModel WHERE roomName LIKE :room")
    abstract fun getChats(room:String):List<MessageModel>
}