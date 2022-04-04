package com.moyerun.moyeorun_android.network.api

import com.moyerun.moyeorun_android.network.Success
import com.moyerun.moyeorun_android.network.calladapter.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{user}/repos")
    suspend fun getUserRepoList(@Path("user") user: String): ApiResponse<Success<Any>>
}