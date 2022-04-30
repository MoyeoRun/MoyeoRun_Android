package com.moyerun.moyeorun_android.profile

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileUiModel(
    val imageUri: Uri = Uri.EMPTY,
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