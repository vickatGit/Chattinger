package com.example.chattinger.di

import android.content.Context
import com.example.chattinger.ui.Activities.Chat.ChatActivity
import com.example.chattinger.ui.Activities.LoginActivity.LoginActivity
import com.example.chattinger.ui.Activities.RoomCreationActivity.RoomCreationActivity
import com.example.chattinger.ui.Activities.RoomsActivity.RoomsActivity

import com.example.chattinger.ui.Activities.SplashActivity.SplashActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DatabaseModule::class])
interface ApplicationComponent {
    fun inject(chatActivity: SplashActivity)
    fun inject(chatActivity: ChatActivity)
    fun inject(chatActivity: LoginActivity)
    fun inject(chatActivity: RoomsActivity)
    fun inject(chatActivity: RoomCreationActivity)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context):ApplicationComponent
    }
}