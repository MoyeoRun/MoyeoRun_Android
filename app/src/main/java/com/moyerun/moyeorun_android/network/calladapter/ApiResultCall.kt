package com.moyerun.moyeorun_android.network.callAdapter

import com.moyerun.moyeorun_android.network.api.Error
import com.moyerun.moyeorun_android.network.client.retrofit
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.*
import java.io.IOException

class ApiResultCall<S>(
    private val delegate: Call<S>
) : Call<ApiResult<S>> {
    override fun enqueue(callback: Callback<ApiResult<S>>) {
        delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                // status code 200번대.
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(ApiResult.Success(body))
                        )
                    } else {
                        val message = "response body is null."
                        val case = "001" // TODO : 바디가 null일 경우 합의된 케이스 번호로 변경 필요함. 001은 임시 번호임.
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(ApiResult.Failure(Error(message, case)))
                        )
                    }
                } else { // status code 200 아닌 다른 것들.
                    val errorBody = response.errorBody()?.let { getErrorResponse(it) }
                    if (errorBody != null) {
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(ApiResult.Failure(errorBody))
                        )
                    } else {
                        val message = "error body is null."
                        val case = "002" // TODO : 에러 바디가 null일 경우 합의된 케이스 번호로 변경 필요함. 002은 임시 번호임.
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(ApiResult.Failure(Error(message, case)))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                val networkResponse = when (t) {
                    is IOException -> {
                        val message = "network error occurred."
                        val case = "003" // TODO : 네트워크 에러 발생 시 합의된 케이스 번호로 변경 필요함. 003은 임시 번호임.
                        Error(message, case)
                    }
                    else -> {
                        val message = "unknown error occurred."
                        val case = "004" // TODO : 언논 에러 발생 시 합의된 케이스 번호로 변경 필요함. 004는 임시 번호임.
                        Error(message, case)
                    }
                }
                callback.onResponse(
                    this@ApiResultCall,
                    Response.success(ApiResult.Failure(networkResponse))
                )
            }
        })
    }

    override fun clone(): Call<ApiResult<S>> = ApiResultCall(delegate.clone())

    override fun execute(): Response<ApiResult<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

    private fun getErrorResponse(errorBody: ResponseBody): Error? {
        return retrofit.responseBodyConverter<Error>(
            Error::class.java,
            Error::class.java.annotations
        ).convert(errorBody)
    }
}