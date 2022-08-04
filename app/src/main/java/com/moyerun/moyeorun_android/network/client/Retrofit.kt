package com.moyerun.moyeorun_android.network.client

import com.moyerun.moyeorun_android.BuildConfig
import com.moyerun.moyeorun_android.network.MoyeorunJsonConverterFactory
import com.moyerun.moyeorun_android.network.api.ApiService
import com.moyerun.moyeorun_android.network.calladapter.ApiResultCallAdapterFactory
import retrofit2.Retrofit

private const val BASE_URL = BuildConfig.BASE_URL

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addCallAdapterFactory(ApiResultCallAdapterFactory())
    .addConverterFactory(MoyeorunJsonConverterFactory.create())
    .build()

val apiService: ApiService = retrofit.create(ApiService::class.java)