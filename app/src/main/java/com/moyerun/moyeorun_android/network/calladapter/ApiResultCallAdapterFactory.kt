package com.moyerun.moyeorun_android.network.calladapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResultCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        // suspend functions wrap the response type in `Call`
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        // check first that the return type is `ParameterizedType`
        if (returnType !is ParameterizedType) {
            return null
        }

        // get the response type inside the `Call` type
        val responseType = getParameterUpperBound(0, returnType)

        // if the response type is not ApiResponse then we can't handle this type, so we return null
        if (getRawType(responseType) != ApiResult::class.java) {
            return null
        }

        // the response type is ApiResponse and should be parameterized
        if (responseType !is ParameterizedType) {
            return null
        }

        val successResponseType = getParameterUpperBound(0, responseType)

        return ApiResultCallAdapter<Any>(successResponseType)
    }
}