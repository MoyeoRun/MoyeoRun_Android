package com.moyerun.moyeorun_android.common

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.moyerun.moyeorun_android.common.extension.*

object Lg {

    private val tag
        get() = run {
            val trace = Thread.currentThread().stackTrace[4]
            val tag = "${trace.fileName} ${trace.lineNumber}"
            tag
        }

    fun d(msg: String) {
        Log.d(tag, msg)
    }

    fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun d(t: Throwable) {
        Log.d(tag, Log.getStackTraceString(t))
    }

    fun d(tag: String, t: Throwable) {
        Log.d(tag, Log.getStackTraceString(t))
    }

    fun fd(msg: String) {
        Log.d(tag, msg)
        Firebase.crashlytics.recordMessage(LogLevel.Debug, msg)
    }

    fun fd(t: Throwable) {
        Log.d(tag, Log.getStackTraceString(t))
        Firebase.crashlytics.recordException(LogLevel.Debug, t)
    }

    fun fd(msg: String, t: Throwable) {
        Log.d(tag, Log.getStackTraceString(t))
        Firebase.crashlytics.recordException(LogLevel.Debug, msg, t)
    }

    fun w(msg: String) {
        Log.w(tag, msg)
    }

    fun w(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    fun w(t: Throwable) {
        Log.w(tag, t)
    }

    fun w(tag: String, t: Throwable) {
        Log.w(tag, t)
    }

    fun fw(msg: String) {
        Log.w(tag, msg)
        Firebase.crashlytics.recordMessage(LogLevel.Warning, msg)
    }

    fun fw(t: Throwable) {
        Log.w(tag, Log.getStackTraceString(t))
        Firebase.crashlytics.recordException(LogLevel.Warning, t)
    }

    fun fw(msg: String, t: Throwable) {
        Log.w(tag, Log.getStackTraceString(t))
        Firebase.crashlytics.recordException(LogLevel.Warning, msg, t)
    }

    fun i(msg: String) {
        Log.i(tag, msg)
    }

    fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    fun i(t: Throwable) {
        Log.i(tag, Log.getStackTraceString(t))
    }

    fun i(tag: String, t: Throwable) {
        Log.i(tag, Log.getStackTraceString(t))
    }

    fun fi(msg: String) {
        Log.i(tag, msg)
        Firebase.crashlytics.recordMessage(LogLevel.Info, msg)
    }

    fun fi(t: Throwable) {
        Log.i(tag, Log.getStackTraceString(t))
        Firebase.crashlytics.recordException(LogLevel.Info, t)
    }

    fun fi(msg: String, t: Throwable) {
        Log.i(tag, Log.getStackTraceString(t))
        Firebase.crashlytics.recordException(LogLevel.Info, msg, t)
    }

    fun e(msg: String) {
        Log.e(tag, msg)
    }

    fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    fun e(t: Throwable) {
        Log.e(tag, Log.getStackTraceString(t))
    }

    fun e(tag: String, t: Throwable) {
        Log.e(tag, Log.getStackTraceString(t))
    }

    fun fe(msg: String) {
        Log.e(tag, msg)
        Firebase.crashlytics.recordMessage(LogLevel.Error, msg)
    }

    fun fe(t: Throwable) {
        Log.e(tag, Log.getStackTraceString(t))
        Firebase.crashlytics.recordException(LogLevel.Error, t)
    }

    fun fe(msg: String, t: Throwable) {
        Log.e(tag, Log.getStackTraceString(t))
        Firebase.crashlytics.recordException(LogLevel.Error, msg, t)
    }
}