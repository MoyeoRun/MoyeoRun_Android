package com.moyerun.moyeorun_android.network.api

import com.moyerun.moyeorun_android.network.calladapter.ApiResult
import com.moyerun.moyeorun_android.network.model.TestSuccess
import retrofit2.http.GET

interface ApiService {

    @GET()
    suspend fun getSuccessTest(): ApiResult<TestSuccess>
}