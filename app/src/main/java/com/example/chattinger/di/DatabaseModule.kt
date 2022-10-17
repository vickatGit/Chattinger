package com.example.chattinger.di

import android.content.Context
import androidx.room.Room
import com.example.chattinger.data.db.ChatDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideChatDb(context: Context): ChatDb {
        return Room.databaseBuilder(context,ChatDb::class.java,"ChatDB").build()
    }
}