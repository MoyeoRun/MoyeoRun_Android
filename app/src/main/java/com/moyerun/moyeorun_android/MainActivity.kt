package com.moyerun.moyeorun_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.network.callAdapter.ApiResult
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
            val response = apiService.getUserRepoList("choheeis")
            when (response) {
                is ApiResult.Success -> {
                    Lg.d("getUserRepoList Success ${response.body.data}") }
                is ApiResult.Failure -> {
                    Lg.d("getUserRepoList ApiError ${response.errorBody.message}")
                }
            }
        }
    }
}