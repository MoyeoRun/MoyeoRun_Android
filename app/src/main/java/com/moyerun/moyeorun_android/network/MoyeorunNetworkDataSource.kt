package com.moyerun.moyeorun_android.network

import com.moyerun.moyeorun_android.login.data.model.SignInResponse
import com.moyerun.moyeorun_android.login.data.model.SignUpRequest
import com.moyerun.moyeorun_android.network.api.Success
import com.moyerun.moyeorun_android.network.calladapter.ApiResult

interface MoyeorunNetworkDataSource {

    suspend fun signUp(signUpRequest: SignUpRequest): ApiResult<Success<SignInResponse>>
}