package com.moyerun.moyeorun_android.login

import com.moyerun.moyeorun_android.common.exceptions.ApiException
import com.moyerun.moyeorun_android.network.api.Error

class AuthException(url: String, error: Error): ApiException(url, error)