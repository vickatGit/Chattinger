package com.example.chattinger.ui.Activities.SplashActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chattinger.data.Repository.ChatRepository
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(val repo:ChatRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(repo) as T
    }
}