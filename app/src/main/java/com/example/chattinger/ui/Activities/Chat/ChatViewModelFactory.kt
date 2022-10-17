package com.example.chattinger.ui.Activities.Chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chattinger.data.Repository.ChatRepository
import javax.inject.Inject

class ChatViewModelFactory @Inject constructor(private val repo:ChatRepository) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(repo) as T
    }
}