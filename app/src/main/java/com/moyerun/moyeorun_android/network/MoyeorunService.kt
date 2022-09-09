package com.moyerun.moyeorun_android.network

import com.moyerun.moyeorun_android.login.data.model.SignInRequest
import com.moyerun.moyeorun_android.login.data.model.SignInResponse
import com.moyerun.moyeorun_android.login.data.model.SignUpRequest
import com.moyerun.moyeorun_android.network.api.Success
import com.moyerun.moyeorun_android.network.calladapter.ApiResult
import retrofit2.http.Body
import retrofit2.http.POST

interface MoyeorunService {

    @POST("/api/auth/sign-in")
    suspend fun signIn(@Body signInRequest: SignInRequest): ApiResult<SignInResponse>

    @POST("/api/auth/sign-up")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): ApiResult<SignInResponse>
}