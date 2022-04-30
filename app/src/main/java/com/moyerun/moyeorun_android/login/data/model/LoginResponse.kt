package com.moyerun.moyeorun_android.login.data.model

data class LoginResponse(
    val token: TokenPair,
    val isNewUser: Boolean,
    val userId: String
)

data class TokenPair(
    val accessToken: Token,
    val refreshToken: Token
)