package com.moyerun.moyeorun_android.login.ui

import com.moyerun.moyeorun_android.profile.SignUpMetaData

sealed class LoginEvent {
    object RegisteredUser : LoginEvent()
    data class NewUser(val signUpMetaData: SignUpMetaData) : LoginEvent()
    object Error : LoginEvent()
}