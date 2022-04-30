package com.moyerun.moyeorun_android.login.data

import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.login.ProviderType
import com.moyerun.moyeorun_android.login.data.model.LoginRequest
import com.moyerun.moyeorun_android.login.data.model.LoginResponse
import com.moyerun.moyeorun_android.login.data.model.Token
import com.moyerun.moyeorun_android.login.data.model.TokenPair
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun signIn(idToken: String, providerType: ProviderType): LoginResponse {
         return withContext(coroutineDispatcher) {
             val request = LoginRequest(idToken, providerType.name.uppercase())
             // Todo: 모여런 서버 signIn
             Lg.d("Try sign in ! : $request")
             LoginResponse(TokenPair(Token("", ""), Token("", "")), true, "1234")
         }
    }
}