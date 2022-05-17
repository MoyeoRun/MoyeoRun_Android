package com.moyerun.moyeorun_android.network.calladapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ApiResultCallAdapter<T>(
    private val returnType: Type
) : CallAdapter<T, Call<ApiResult<T>>> {
    override fun responseType(): Type = returnType

    override fun adapt(call: Call<T>): Call<ApiResult<T>> = ApiResultCall(call)
}