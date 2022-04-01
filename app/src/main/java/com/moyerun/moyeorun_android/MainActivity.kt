package com.moyerun.moyeorun_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.network.callAdapter.NetworkResult
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
                is NetworkResult.Success -> {
                    Lg.d("getUserRepoList Success") }
                is NetworkResult.Failure -> {
                    Lg.d("getUserRepoList ApiError")
                }
                is NetworkResult.NetworkError -> {
                    // 네트워크 에러와 언논 에러는 전처리되면 좋을 듯
                    // Success랑 API Error만 뷰 모델에서 관리하도록.
                    Lg.d("getUserRepoList NetworkError")
                }
                is NetworkResult.UnknownError -> { Lg.d("getUserRepoList UnknownError") }
            }

            val response2 = apiService.getAppName()
            when (response2) {
                is NetworkResult.Success -> { Lg.d("getAppName Success") }
                is NetworkResult.Failure -> {
                    // 비즈니스 로직 상의 에러
                    // 이넘 클래스로 별도 관리하는 에러 코드와 비교하여 알맞은 로직 작성
                    Lg.d("getAppName ApiError")
                }
                is NetworkResult.NetworkError -> { Lg.d("getAppName NetworkError") }
                is NetworkResult.UnknownError -> { Lg.d("getAppName UnknownError") }
            }
        }
    }
}