package com.moyerun.moyeorun_android.network

import com.moyerun.moyeorun_android.login.ProviderType
import com.moyerun.moyeorun_android.login.data.model.SignInRequest
import com.moyerun.moyeorun_android.login.data.model.SignInResponse
import com.moyerun.moyeorun_android.login.data.model.SignUpRequest
import com.moyerun.moyeorun_android.network.api.Success
import com.moyerun.moyeorun_android.network.calladapter.ApiResult

class MoyeorunNetworkDataSourceImpl(
    private val moyeorunService: MoyeorunService
) : MoyeorunNetworkDataSource {

    override suspend fun signIn(
        idToken: String,
        providerType: ProviderType
    ): ApiResult<Success<SignInResponse>> {
        return moyeorunService.signIn(
            SignInRequest(
                idToken = idToken,
                providerType = providerType.name.uppercase()
            )
        )
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): ApiResult<Success<SignInResponse>> {
        return moyeorunService.signUp(signUpRequest)
    }
}