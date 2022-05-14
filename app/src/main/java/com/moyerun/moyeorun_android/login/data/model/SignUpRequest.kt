package com.moyerun.moyeorun_android.login.data.model

data class SignUpRequest(
    val idToken: String,
    val provider: String,
    val nickName: String,
    val gender: String
)