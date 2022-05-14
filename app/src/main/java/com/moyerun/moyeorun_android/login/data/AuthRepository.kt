package com.moyerun.moyeorun_android.login.data

import com.moyerun.moyeorun_android.common.di.IODispatcher
import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.login.ProviderType
import com.moyerun.moyeorun_android.login.data.model.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AuthRepository {
    suspend fun signUp(signUpRequest: SignUpRequest)
    suspend fun signIn(idToken: String, providerType: ProviderType): LoginResponse
}

class AuthRepositoryImpl @Inject constructor(
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
): AuthRepository {

    override suspend fun signIn(idToken: String, providerType: ProviderType): LoginResponse {
         return withContext(coroutineDispatcher) {
             val request = SignInRequest(idToken, providerType.name.uppercase())
             // Todo: 모여런 서버 signIn
             Lg.d("Try sign in ! : $request")
             LoginResponse(TokenPair(Token("", ""), Token("", "")), true, "1234")
         }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest) {
        Lg.d("SignUp : $signUpRequest")
    }
}