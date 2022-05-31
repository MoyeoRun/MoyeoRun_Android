package com.moyerun.moyeorun_android.network.calladapter

import com.moyerun.moyeorun_android.network.api.ApiFailure
import com.moyerun.moyeorun_android.network.api.ApiSuccess

/**
 * Success : API 호출 성공 시, body를 Wrapping 합니다.
 * Failure : API 호출 실패 시, throwable을 Wrpping 합니다.
 */
sealed class ApiResult<T> {
    data class Success<T>(val body: T) : ApiResult<ApiSuccess<T>>()
    data class Failure<T>(val throwable: Throwable, val errorBody: T) : ApiResult<ApiFailure>()
}