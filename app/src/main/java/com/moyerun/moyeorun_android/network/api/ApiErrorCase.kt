package com.moyerun.moyeorun_android.network.api

import com.moyerun.moyeorun_android.common.exceptions.ApiException
import com.moyerun.moyeorun_android.login.AuthException

enum class ApiErrorCase(val case: String) {
    // 서버와 합의된 에러 케이스들을 정의합니다.
    AUTHORIZATION_FAIL("101"),
    EXPIRE_JWT("104"),
    NOT_LOGIN("105"),
    DUPLICATE_NICKNAME("110"),
    DUPLICATE_USER("111"),
    UNKNOWN("999");

    companion object {
        fun findError(case: String): ApiErrorCase {
            return values().find { it.case == case } ?: UNKNOWN
        }

        fun getException(url: String, error: Error, cause: Throwable? = null): Throwable {
            return when (findError(error.case)) {
                AUTHORIZATION_FAIL,
                EXPIRE_JWT,
                NOT_LOGIN,
                DUPLICATE_NICKNAME,
                DUPLICATE_USER -> AuthException(url, error)
                else -> ApiException(url, error)
            }
        }
    }
}