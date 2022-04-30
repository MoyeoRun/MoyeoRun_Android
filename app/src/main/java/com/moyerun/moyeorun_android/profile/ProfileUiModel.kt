package com.moyerun.moyeorun_android.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileUiModel(
    val imageUrl: String = "",
    val name: String = "",
    val nickname: String = ""
): Parcelable

sealed class ProfileEvent {
    object SuccessSignUp: ProfileEvent()
    data class Error(val error: ProfileError): ProfileEvent()
}

enum class ProfileError {
    WRONG_ACCESS
}