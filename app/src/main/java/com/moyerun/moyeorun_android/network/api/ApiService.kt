package com.moyerun.moyeorun_android.network.api

import com.moyerun.moyeorun_android.network.calladapter.ApiResult
import retrofit2.http.GET

interface ApiService {
    @GET("success")
    suspend fun getSuccess(): ApiResult<Success<Any>>

    @GET("failure")
    suspend fun getFailure(): ApiResult<Success<Any>>
}