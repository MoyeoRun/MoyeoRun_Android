package com.moyerun.moyeorun_android.network.calladapter

import com.google.gson.Gson
import com.moyerun.moyeorun_android.common.exceptions.NetworkException
import com.moyerun.moyeorun_android.network.api.ApiErrorCase
import com.moyerun.moyeorun_android.network.api.ApiFailure
import okhttp3.Request
import okio.Timeout
import retrofit2.*

class ApiResultCall<T>(
    private val delegate: Call<T>
) : Call<ApiResult<T>> {

    override fun enqueue(callback: Callback<ApiResult<T>>) {
        delegate.enqueue(object : Callback<T> {
            // 이거 그대로 오버라이딩하면 됨...
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val requestUrl = delegate.request().url().toString()

                // status code가 200 ~ 299 일 때.
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success()
                        )
                    } else {

                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    //    override fun enqueue(callback: Callback<ApiResult<T>>) {
//        delegate.enqueue(object : Callback<S> {
//            override fun onResponse(call: Call<S>, response: Response<S>) {
//                val requestUrl = delegate.request().url().toString()
//
//                // status code 200번대.
//                if (response.isSuccessful) {
//                    val body = response.body()
//                    if (body != null) {
//                        callback.onResponse(
//                            this@ApiResultCall,
//                            Response.success(ApiResult.Success(body))
//                        )
//                    } else {
//                        callback.onResponse(
//                            this@ApiResultCall,
//                            Response.success(
//                                ApiResult.Failure(
//                                    NetworkException(
//                                        "Response from $requestUrl was null " +
//                                                "but response body type was decleared as non-null",
//                                        HttpException(response)
//                                    )
//                                )
//                            )
//                        )
//                    }
//                } else { // status code 200번대 아님.
//                    val errorBody = response.errorBody()
//                    if (errorBody != null) {
//                        val errorResponse = Gson().fromJson(errorBody.string(), ApiFailure::class.java)
//                        callback.onResponse(
//                            this@ApiResultCall,
//                            Response.success(
//                                ApiResult.Failure(
//                                    ApiErrorCase.getException(
//                                        requestUrl,
//                                        errorResponse,
//                                        HttpException(response)
//                                    )
//                                )
//                            )
//                        )
//                    } else {
//                        callback.onResponse(
//                            this@ApiResultCall,
//                            Response.success(
//                                ApiResult.Failure(
//                                    NetworkException(
//                                        requestUrl,
//                                        HttpException(response)
//                                    )
//                                )
//                            )
//                        )
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<S>, throwable: Throwable) {
//                callback.onResponse(
//                    this@ApiResultCall,
//                    Response.success(
//                        ApiResult.Failure(
//                            NetworkException(
//                                call.request().url().toString(),
//                                throwable
//                            )
//                        )
//                    )
//                )
//            }
//        })
//    }
//
    override fun clone(): Call<ApiResult<T>> = ApiResultCall(delegate.clone())

    override fun execute(): Response<ApiResult<T>> {
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