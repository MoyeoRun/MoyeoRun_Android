package com.moyerun.moyeorun_android.network.callAdapter

import java.io.IOException

sealed class NetworkResult<out T : Any, out U : Any> {
    data class Success<T : Any>(val body: T) : NetworkResult<T, Nothing>()
    data class Failure<U : Any>(val body: U, val code: Int) : NetworkResult<Nothing, U>()
    data class NetworkError(val error: IOException) : NetworkResult<Nothing, Nothing>()
    data class UnknownError(val error: Throwable?) : NetworkResult<Nothing, Nothing>()
}