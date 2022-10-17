package com.example.chattinger.ui.Activities.RoomsActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chattinger.data.Repository.ChatRepository
import com.example.chattinger.ui.Activities.RoomCreationActivity.RoomCreationViewModel
import javax.inject.Inject

class RoomsViewModelFactory @Inject constructor(val repo: ChatRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoomsVideModel(repo) as T
    }

}