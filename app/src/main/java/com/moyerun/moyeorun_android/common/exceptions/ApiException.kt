package com.moyerun.moyeorun_android.common.exceptions

import com.moyerun.moyeorun_android.network.api.ApiFailure

class ApiException(val url: String, val apiFailure: ApiFailure) : RuntimeException() {
    val case: String
        get() = apiFailure.case

    override val message: String?
        get() = apiFailure.message
}