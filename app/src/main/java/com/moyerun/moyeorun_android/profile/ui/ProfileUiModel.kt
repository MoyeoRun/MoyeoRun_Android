package com.moyerun.moyeorun_android.profile.ui

import android.net.Uri
import android.os.Parcelable
import com.moyerun.moyeorun_android.login.data.model.SignUpRequest
import com.moyerun.moyeorun_android.login.SignUpMetaData
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileUiModel(
    val imageUri: Uri = Uri.EMPTY,
    val name: String = "",
    val nickname: String = "",
    val gender: Gender = Gender.NONE
) : Parcelable

fun ProfileUiModel.toSignUpRequest(signUpMetaData: SignUpMetaData): SignUpRequest {
    return SignUpRequest(
        signUpMetaData.idToken,
        signUpMetaData.providerType.name,
        nickname,
        gender.name
    )
}

enum class ProfileEvent {
    SUCCESS_SIGN_UP
}

enum class ProfileError {
    WRONG_ACCESS, UNKNOWN
}

enum class Gender {
    MAN, WOMAN, NONE
}