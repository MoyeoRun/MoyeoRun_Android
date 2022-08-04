package com.moyerun.moyeorun_android.network.calladapter

/**
 * Success : API 호출 성공 시, body를 Wrapping 합니다.
 * Failure : API 호출 실패 시, throwable을 Wrpping 합니다.
 */
sealed class ApiResult<out T> {
    data class Success<T>(val body: T) : ApiResult<T>()
    data class Failure(val throwable: Throwable) : ApiResult<Nothing>()
}

inline fun <T> ApiResult<T>.onSuccess(action: (data: T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) {
        action.invoke(body)
    }
    return this
}

inline fun <T> ApiResult<T>.onFailure(action: (throwable: Throwable) -> Unit): ApiResult<T> {
    if (this is ApiResult.Failure) {
        action.invoke(throwable)
    }
    return this
}