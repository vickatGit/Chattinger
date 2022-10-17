package com.example.chattinger.ui.Activities.RoomCreationActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chattinger.data.Repository.ChatRepository

class RoomCreationViewModel(val repo: ChatRepository): ViewModel() {
    fun createRoom(nameOfRoom: String): MutableLiveData<Boolean> {
        return repo.createRoom(nameOfRoom)
    }
}