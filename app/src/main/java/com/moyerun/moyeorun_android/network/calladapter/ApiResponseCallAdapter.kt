package com.moyerun.moyeorun_android.network.calladapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ApiResponseCallAdapter<T: Any>(
    private val returnType: Type
) : CallAdapter<T, Call<ApiResponse<T>>> {

    override fun responseType(): Type = returnType

    override fun adapt(call: Call<T>): Call<ApiResponse<T>> = ApiResponseCall(call)
}