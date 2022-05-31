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

        // suspend 함수의 리턴 타입은 `Call`로 Wrapping 되어 있어야 한다.
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        // suspend 함수의 리턴 타입은 제네릭 타입이어야 한다.
        if (returnType !is ParameterizedType) {
            return null
        }

        // `Call`로 Wrapping 되어 있는 ApiResult 타입을 추출한다.
        val apiResultType = getParameterUpperBound(0, returnType)

        // ApiResult 타입은 `ApiResult`로 Wrapping 되어 있어야 한다.
        if (getRawType(apiResultType) != ApiResult::class.java) {
            return null
        }

        // ApiResult 타입은 제네릭 타입이어야 한다.
        if (apiResultType !is ParameterizedType) {
            return null
        }

        // `ApiResult`로 Wrapping 되어 있는 response 타입을 추출한다.
        val responseType = getParameterUpperBound(0, apiResultType)

        // response 타입은 제네릭 타입이어야 한다.
        if (responseType !is ParameterizedType) {
            return null
        }

        return ApiResultCallAdapter<Any>(responseType)
    }
}