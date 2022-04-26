package com.moyerun.moyeorun_android.common.extension

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

fun Activity.toast(msg: String, isShort: Boolean = true) {
    Toast.makeText(this, msg, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
}

inline fun FragmentActivity.showAllowingStateLoss(
    tag: String?,
    dialogFragmentFactory: () -> DialogFragment
) {
    supportFragmentManager.showAllowingStateLoss(tag, dialogFragmentFactory)
}