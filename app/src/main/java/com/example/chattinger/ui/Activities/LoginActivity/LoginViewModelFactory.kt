package com.example.chattinger.ui.Activities.LoginActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chattinger.data.Repository.ChatRepository
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(val repo:ChatRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repo) as T
    }

}