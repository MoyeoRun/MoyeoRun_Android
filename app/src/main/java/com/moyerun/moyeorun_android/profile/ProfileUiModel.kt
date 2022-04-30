package com.moyerun.moyeorun_android.profile

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.IdRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileUiModel(
    val imageUri: Uri = Uri.EMPTY,
    val name: String = "",
    val nickname: String = "",
    val gender: Gender = Gender.NONE
): Parcelable

sealed class ProfileEvent {
    object SuccessSignUp: ProfileEvent()
    data class Error(val error: ProfileError): ProfileEvent()
}

enum class ProfileError {
    WRONG_ACCESS
}

enum class Gender {
    MAN, WOMAN, NONE
}