package com.moyerun.moyeorun_android.network.calladapter

/**
 * Success : API 호출 성공 시, body를 Wrapping 합니다.
 * Failure : API 호출 실패 시, errorBody를 Wrpping 합니다.
 */
sealed class ApiResult<out T> {
    data class Success<T>(val body: T) : ApiResult<T>()
    data class Failure(val exception: Throwable) : ApiResult<Nothing>()
}