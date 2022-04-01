package com.moyerun.moyeorun_android.network.callAdapter

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import java.lang.UnsupportedOperationException

internal class NetworkResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<NetworkResult<S, E>> {

    override fun enqueue(callback: Callback<NetworkResult<S, E>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    // status code가 200번대 일 때
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResult.Success(body))
                        )
                    } else {
                        // body 없을 때 별도 에러 만들어도 될 듯.
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResult.UnknownError(null))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (e: Exception) {
                            null
                        }
                    }

                    if (errorBody != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResult.Failure(errorBody, code))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResult.UnknownError(null))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                val networkResponse = when (t) {
                    is IOException -> NetworkResult.NetworkError(t)
                    else -> NetworkResult.UnknownError(t)
                }
                // 여기서 전처리 하면 됨.
                callback.onResponse(
                    this@NetworkResponseCall,
                    Response.success(networkResponse)
                )
            }
        })
    }

    override fun clone(): Call<NetworkResult<S, E>> = NetworkResponseCall(delegate.clone(), errorConverter)

    override fun execute(): Response<NetworkResult<S, E>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}