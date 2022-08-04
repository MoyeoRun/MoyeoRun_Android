package com.moyerun.moyeorun_android.login.data.impl

import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.common.di.IODispatcher
import com.moyerun.moyeorun_android.login.ProviderType
import com.moyerun.moyeorun_android.login.data.AuthRepository
import com.moyerun.moyeorun_android.login.data.model.*
import com.moyerun.moyeorun_android.network.MoyeorunNetworkDataSource
import com.moyerun.moyeorun_android.network.api.Success
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
    ): ApiResult<Success<SignInResponse>> {
        return withContext(coroutineDispatcher) {
            network.signIn(idToken = idToken, providerType = providerType)
        }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): ApiResult<Success<SignInResponse>> {
        return withContext(coroutineDispatcher) {
            network.signUp(signUpRequest)
        }
    }
}