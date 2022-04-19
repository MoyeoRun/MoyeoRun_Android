package com.moyerun.moyeorun_android.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileUiModel(
    val imageUrl: String = "",
    val name: String = "",
    val nickname: String = ""
): Parcelable