package com.example.chattinger.ui.Activities.LoginActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.chattinger.data.Repository.ChatRepository

class LoginViewModel(val repo:ChatRepository): ViewModel() {

    fun loginUser(userName: String) {
        repo.loginUser(userName)
    }
}