package com.moyerun.moyeorun_android.network.api

/**
 * 서버 팀과 합의한 성공/실패 응답 값에 대한 모델입니다.
 */
data class ApiSuccess<T>(
    val data: T
)

data class ApiFailure(
    val message: String?,
    val case: String
)