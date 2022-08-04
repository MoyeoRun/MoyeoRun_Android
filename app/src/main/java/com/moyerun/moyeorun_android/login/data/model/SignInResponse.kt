package com.moyerun.moyeorun_android.login.data.model

data class SignInResponse(
    val token: TokenPair,
    val userId: String,
    val isNewUser: Boolean,
)

data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
)