package com.moyerun.moyeorun_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.network.calladapter.ApiResponse
import com.moyerun.moyeorun_android.network.client.apiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiCallTest()
    }

    private fun apiCallTest() {
        GlobalScope.launch {
            val response1 = apiService.getUserRepoList("choheeis")
            when (response1) {
                is ApiResponse.Success -> {
                    Lg.d("getUserRepoList Success") }
                is ApiResponse.Failure -> {

                    }
                }
            }
        }
    }
}