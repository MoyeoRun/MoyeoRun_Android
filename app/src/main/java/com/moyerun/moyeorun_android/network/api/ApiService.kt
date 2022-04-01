package com.moyerun.moyeorun_android.network.api

import com.moyerun.moyeorun_android.network.Failure
import com.moyerun.moyeorun_android.network.Success
import com.moyerun.moyeorun_android.network.callAdapter.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{user}/repos")
    suspend fun getUserRepoList(@Path("user") user: String): NetworkResult<Success<Any>, Failure<Any>>

    @GET("appName")
    suspend fun getAppName(): NetworkResult<Success<Any>, Failure<Any>>
}