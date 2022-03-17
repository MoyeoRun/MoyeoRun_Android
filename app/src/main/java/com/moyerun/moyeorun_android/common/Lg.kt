package com.moyerun.moyeorun_android.common

import android.util.Log

object Lg {

    private val tag
        get() = run {
            val trace = Thread.currentThread().stackTrace[4]
            val tag = "${trace.fileName}:${trace.lineNumber}"
            tag
        }

    fun d(msg: String) {
        Log.d(tag, msg)
    }

    fun d(t: Throwable) {
        Log.d(tag, Log.getStackTraceString(t))
    }

    fun w(msg: String) {
        Log.w(tag, msg)
    }

    fun w(t: Throwable) {
        Log.w(tag, t)
    }

    fun i(msg: String) {
        Log.i(tag, msg)
    }

    fun i(t: Throwable) {
        Log.i(tag, Log.getStackTraceString(t))
    }
}