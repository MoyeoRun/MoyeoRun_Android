package com.moyerun.moyeorun_android.common.extension

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

fun Fragment.toast(msg: String, isShort: Boolean = false) {
    Toast.makeText(context, msg, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
}

fun Fragment.putStringToArgument(key: String, value: String?) {
    val args = arguments
    if (args == null) {
        arguments = bundleOf(key to value)
    } else {
        args.apply {
            putString(key, value)
        }
    }
}

fun Fragment.getStringFromArgument(key: String, defaultValue: String = ""): String {
    return arguments?.getString(key, defaultValue)?: defaultValue
}

fun Fragment.putBooleanToArgument(key: String, value: Boolean) {
    val args = arguments
    if (args == null) {
        arguments = bundleOf(key to value)
    } else {
        args.apply {
            putBoolean(key, value)
        }
    }
}

fun Fragment.getBooleanFromArgument(key: String, value: Boolean): Boolean {
    return arguments?.getBoolean(key, value)?: value
}