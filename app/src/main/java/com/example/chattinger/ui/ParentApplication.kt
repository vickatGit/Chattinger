package com.example.chattinger.ui

import android.app.Application
import com.example.chattinger.di.ApplicationComponent
import com.example.chattinger.di.DaggerApplicationComponent


class ParentApplication: Application() {
    lateinit var applicationComponent:ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent=DaggerApplicationComponent.factory().create(this)
    }
}