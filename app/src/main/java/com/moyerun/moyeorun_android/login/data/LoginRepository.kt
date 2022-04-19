package com.moyerun.moyeorun_android.login.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    // Todo: 모여런 서버 붙일 때 반환 타입 조정
    suspend fun signIn(idToken: String): LoginResponse {
         return withContext(coroutineDispatcher) {
             // Todo: 모여런 서버 signIn
             LoginResponse(TokenPair(Token("", ""), Token("", "")), true, "1234")
         }
    }
}