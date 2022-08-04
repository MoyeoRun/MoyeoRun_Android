package com.moyerun.moyeorun_android.login.data.impl

import com.moyerun.moyeorun_android.common.di.IODispatcher
import com.moyerun.moyeorun_android.login.ProviderType
import com.moyerun.moyeorun_android.login.data.AuthRepository
import com.moyerun.moyeorun_android.login.data.model.SignInResponse
import com.moyerun.moyeorun_android.login.data.model.SignUpRequest
import com.moyerun.moyeorun_android.network.MoyeorunNetworkDataSource
import com.moyerun.moyeorun_android.network.calladapter.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val network: MoyeorunNetworkDataSource,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : AuthRepository {

    override suspend fun signIn(
        idToken: String,
        providerType: ProviderType
    ): ApiResult<SignInResponse> {
        return withContext(coroutineDispatcher) {
            network.signIn(idToken = idToken, providerType = providerType)
        }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): ApiResult<SignInResponse> {
        return withContext(coroutineDispatcher) {
            network.signUp(signUpRequest)
        }
    }
}