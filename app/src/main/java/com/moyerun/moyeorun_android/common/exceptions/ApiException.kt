package com.moyerun.moyeorun_android.common.exceptions

import com.moyerun.moyeorun_android.network.api.Error

open class ApiException(val url: String, val error: Error) : RuntimeException() {
    val case: String
        get() = error.case

    override val message: String?
        get() = error.message

    override fun toString(): String {
        return "${super.toString()} url: $url, error: $error"
    }
}