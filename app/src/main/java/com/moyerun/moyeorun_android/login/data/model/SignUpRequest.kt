package com.moyerun.moyeorun_android.login.data.model

data class SignUpRequest(
    val idToken: String,
    val providerType: String,
    val nickName: String,
    val gender: String,
    val image: String
)