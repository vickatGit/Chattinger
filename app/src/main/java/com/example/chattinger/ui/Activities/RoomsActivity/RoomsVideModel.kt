package com.example.chattinger.ui.Activities.RoomsActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chattinger.data.Models.RoomModel
import com.example.chattinger.data.Repository.ChatRepository

class RoomsVideModel(val repo: ChatRepository): ViewModel() {
    fun getUserCreatedRooms(): MutableLiveData<ArrayList<RoomModel>> {
        return repo.getUserCreatedRooms()
    }
}