package com.example.chattinger.data.Models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class UserModel(@PrimaryKey val userId:Int=1,val userName:String) : Parcelable
