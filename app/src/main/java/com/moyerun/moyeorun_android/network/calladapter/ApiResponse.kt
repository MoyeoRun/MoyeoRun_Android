package com.moyerun.moyeorun_android.network.calladapter

/**
 * Success : API 호출 성공 시, 데이터를 Wrapping 합니다.
 * Failure : API 호출 실패 시, 에러를 Wrapping 합니다.
 */
sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Failure(val error: Throwable) : ApiResponse<Nothing>()
}