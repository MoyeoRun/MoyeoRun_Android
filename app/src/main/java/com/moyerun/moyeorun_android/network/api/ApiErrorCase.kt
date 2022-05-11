package com.moyerun.moyeorun_android.network.api

import com.moyerun.moyeorun_android.common.exceptions.ApiException

enum class ApiErrorCase(val case: String) {
    // 서버와 합의된 에러 케이스들을 정의합니다.
    NOT_LOGIN("100"), // TODO : 예시입니다. 실제 로그인 작업 시 수정해주세요.
    UNKNOWN("999");

    companion object {
        private fun findError(case: String): ApiErrorCase {
            return values().find { it.case == case } ?: UNKNOWN
        }

        fun getException(url: String, error: Error, cause: Throwable? = null): Throwable {
            return when(findError(error.case)) {
                NOT_LOGIN -> { TODO() }
                else -> ApiException(url, error)
            }
        }
    }
}