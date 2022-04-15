package com.moyerun.moyeorun_android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoyeoRunApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}