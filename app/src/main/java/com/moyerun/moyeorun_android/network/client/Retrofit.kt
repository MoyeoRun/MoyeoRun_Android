package com.moyerun.moyeorun_android.network.client

import com.moyerun.moyeorun_android.BuildConfig
import com.moyerun.moyeorun_android.network.calladapter.ApiResultCallAdapterFactory
import com.moyerun.moyeorun_android.network.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = BuildConfig.BASE_URL

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addCallAdapterFactory(ApiResultCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService: ApiService = retrofit.create(ApiService::class.java)