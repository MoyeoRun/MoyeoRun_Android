package com.moyerun.moyeorun_android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MoyeoRunApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}