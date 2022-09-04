package com.moyerun.moyeorun_android.network.calladapter

import com.google.gson.Gson
import com.moyerun.moyeorun_android.common.exceptions.NetworkException
import com.moyerun.moyeorun_android.network.api.ApiErrorCase
import com.moyerun.moyeorun_android.network.api.Error
import okhttp3.Request
import okio.Timeout
import retrofit2.*

class ApiResultCall<S>(
    private val delegate: Call<S>
) : Call<ApiResult<S>> {
    override fun enqueue(callback: Callback<ApiResult<S>>) {
        delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val requestUrl = delegate.request().url.toString()

                // status code 200번대.
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(ApiResult.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(
                                ApiResult.Failure(
                                    NetworkException(
                                        "Response from $requestUrl was null " +
                                                "but response body type was decleared as non-null",
                                        HttpException(response)
                                    )
                                )
                            )
                        )
                    }
                } else { // status code 200번대 아님.
                    val errorBody = response.errorBody()
                    if (errorBody != null) {
                        val errorResponse = Gson().fromJson(errorBody.string(), Error::class.java)
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(
                                ApiResult.Failure(
                                    ApiErrorCase.getException(
                                        requestUrl,
                                        errorResponse,
                                        HttpException(response)
                                    )
                                )
                            )
                        )
                    } else {
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(
                                ApiResult.Failure(
                                    NetworkException(
                                        requestUrl,
                                        HttpException(response)
                                    )
                                )
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                callback.onResponse(
                    this@ApiResultCall,
                    Response.success(
                        ApiResult.Failure(
                            NetworkException(
                                call.request().url.toString(),
                                throwable
                            )
                        )
                    )
                )
            }
        })
    }

    override fun clone(): Call<ApiResult<S>> = ApiResultCall(delegate.clone())

    override fun execute(): Response<ApiResult<S>> {
        throw UnsupportedOperationException("ApiResultCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

}