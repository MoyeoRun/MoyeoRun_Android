package com.moyerun.moyeorun_android.network.calladapter

import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.UnsupportedOperationException

internal class ApiResponseCall<T : Any>(
    private val delegate: Call<T>
) : Call<ApiResponse<T>> {

    override fun enqueue(callback: Callback<ApiResponse<T>>) {
        delegate.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val body = response.body()

                    if (body != null) {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse.Success(body))
                        )
                    } else {
                        val invocation = call.request().tag(Invocation::class.java)
                        val method = invocation?.method()
                        val message = if (method != null) {
                            "Response from " +
                                    method.declaringClass.name +
                                    '.' +
                                    method.name +
                                    " was null but response body type was declared as non-null"
                        } else {
                            "No tag is attached with Invocation::class.java. So, invocation can't find."
                        }
                        val e = KotlinNullPointerException(message)

                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiResponse.Failure(e))
                        )
                    }
                } else {
                    callback.onResponse(
                        this@ApiResponseCall,
                        Response.success(ApiResponse.Failure(HttpException(response)))
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(
                    this@ApiResponseCall,
                    Response.success(ApiResponse.Failure(t))
                )
            }
        })
    }

    override fun clone(): Call<ApiResponse<T>> = ApiResponseCall(delegate.clone())

    override fun execute(): Response<ApiResponse<T>> {
        throw UnsupportedOperationException("ApiResponseCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}