package com.example.chattinger.ui.Activities.SplashActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chattinger.data.Models.UserModel
import com.example.chattinger.data.Repository.ChatRepository

class SplashViewModel(val repo:ChatRepository): ViewModel() {

    fun getUsers(): MutableLiveData<ArrayList<UserModel>> {
        return repo.getUsers()
    }
}