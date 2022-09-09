package com.moyerun.moyeorun_android.common.extension

import com.google.firebase.crashlytics.FirebaseCrashlytics

fun FirebaseCrashlytics.recordMessage(logLevel: LogLevel, message: String) {
    recordException(FirebaseLogException(logLevel, message))
}

fun FirebaseCrashlytics.recordException(logLevel: LogLevel, throwable: Throwable) {
    recordException(FirebaseLogException(logLevel, throwable))
}

fun FirebaseCrashlytics.recordException(logLevel: LogLevel, message: String, throwable: Throwable) {
    recordException(FirebaseLogException(logLevel, message, throwable))
}

/**
 * Firebase 로깅 (recordException) 을 위해 사용하는 Exception
 * 로깅하고자하는 Exception 은 cause 로 다루고, 로그 레벨과 메시지를 message 에 담는다.
 */
class FirebaseLogException : Exception {
    constructor(logLevel: LogLevel, message: String) : super("$logLevel : $message")

    constructor(logLevel: LogLevel, cause: Throwable) : super("$logLevel : $cause", cause)

    constructor(
        logLevel: LogLevel,
        message: String,
        cause: Throwable
    ) : super("$logLevel : $message", cause)
}

enum class LogLevel {
    Info, Debug, Error, Warning;

    override fun toString(): String {
        return "LogLevel [${super.toString()}]"
    }
}