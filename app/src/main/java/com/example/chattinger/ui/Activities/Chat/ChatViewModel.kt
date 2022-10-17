package com.example.chattinger.ui.Activities.Chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chattinger.data.Models.MessageModel
import com.example.chattinger.data.Repository.ChatRepository

class ChatViewModel(val repo:ChatRepository): ViewModel() {
    val chatsLiveData= MutableLiveData<List<MessageModel>>()
    fun getChats(): MutableLiveData<List<MessageModel>> {
        return repo.getChats()
    }

    fun insertMessage(message: MessageModel) {
        return repo.insertMessage(message)
    }

    fun getRoomChats(roomName: String): MutableLiveData<ArrayList<MessageModel>> {
        return repo.getMessages(roomName)
    }
}