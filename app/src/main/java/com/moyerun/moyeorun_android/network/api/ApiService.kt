package com.moyerun.moyeorun_android.network.api

import com.moyerun.moyeorun_android.network.callAdapter.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{user}/repos")
    suspend fun getUserRepoList(@Path("user") user: String): ApiResult<Success<Any>>
}