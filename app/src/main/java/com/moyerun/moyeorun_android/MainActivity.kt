package com.moyerun.moyeorun_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.common.exceptions.ApiException
import com.moyerun.moyeorun_android.network.calladapter.ApiResult
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
            val response1 = apiService.getSuccess()
            val response2 = apiService.getFailure()
            when (response1) {
                is ApiResult.Success -> {
                    Lg.d("postman test Success response 2 ${response1.body.data}")
                }
                is ApiResult.Failure -> {
                    when (response1.exception) {
                        is ApiException -> {
                            Lg.d("postman test Failure response 2 ${response1.exception.message}")
                        }
                    }
                }
            }
            when (response2) {
                is ApiResult.Success -> {
                    Lg.d("postman test Success response 3 ${response2.body.data}")
                }
                is ApiResult.Failure -> {
                    when (response2.exception) {
                        is ApiException -> {
                            Lg.d("postman test Failure response 2 ${response2.exception.message}")
                        }
                    }
                }
            }
        }
    }
}