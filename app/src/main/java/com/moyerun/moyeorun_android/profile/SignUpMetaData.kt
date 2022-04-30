package com.moyerun.moyeorun_android.profile

import android.os.Parcelable
import com.moyerun.moyeorun_android.login.ProviderType
import kotlinx.parcelize.Parcelize

/**
 * 회원가입을 위해 프로필 설정 화면으로 진입 시
 * 회원가입 API 호출에 필요한 메타 데이터를 전달하기 위한
 * DataHolder
 */
@Parcelize
data class SignUpMetaData(
    val idToken: String,
    val providerType: ProviderType
) : Parcelable
