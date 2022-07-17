package com.moyerun.moyeorun_android.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moyerun.moyeorun_android.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room)
    }
}