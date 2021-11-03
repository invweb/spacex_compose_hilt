package com.spacex

import android.app.Application
import com.spacex.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SpaceXApp : Application() {
    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(this)!!
    }
}