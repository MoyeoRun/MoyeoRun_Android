package com.moyerun.moyeorun_android.login.data

import com.moyerun.moyeorun_android.login.ProviderType
import com.moyerun.moyeorun_android.login.data.model.SignInResponse
import com.moyerun.moyeorun_android.login.data.model.SignUpRequest
import com.moyerun.moyeorun_android.network.api.Success
import com.moyerun.moyeorun_android.network.calladapter.ApiResult

interface AuthRepository {
    suspend fun signIn(
        idToken: String,
        providerType: ProviderType
    ): ApiResult<SignInResponse>

    suspend fun signUp(signUpRequest: SignUpRequest): ApiResult<SignInResponse>
}