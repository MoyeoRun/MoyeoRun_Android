package com.moyerun.moyeorun_android.network

import com.moyerun.moyeorun_android.login.data.model.SignInResponse
import com.moyerun.moyeorun_android.login.data.model.SignUpRequest
import com.moyerun.moyeorun_android.network.api.Success
import com.moyerun.moyeorun_android.network.calladapter.ApiResult

class MoyeorunNetworkDataSourceImpl(
    private val moyeorunService: MoyeorunService
): MoyeorunNetworkDataSource {

    override suspend fun signUp(signUpRequest: SignUpRequest): ApiResult<Success<SignInResponse>> {
        return moyeorunService.signUp(signUpRequest)
    }
}