package com.moyerun.moyeorun_android.network

/**
 * 서버 팀과 합의한 성공/실패 응답 값에 대한 모델입니다.
 */
data class Success<T>(
    val message: String,
    val data: T
)

data class Failure<T>(
    val message: String,
    val error: T
)