package com.example.chattinger.data.Models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class RoomModel( val roomName:String,@PrimaryKey(autoGenerate = true) val roomId:Int=0) :
    Parcelable
