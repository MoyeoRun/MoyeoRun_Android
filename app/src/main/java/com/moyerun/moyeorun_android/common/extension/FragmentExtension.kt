package com.moyerun.moyeorun_android.common.extension

import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.moyerun.moyeorun_android.R

fun Fragment.toast(msg: String, isShort: Boolean = false) {
    Toast.makeText(context, msg, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
}

inline fun FragmentManager?.showAllowingStateLoss(
    tag: String?,
    dialogFragmentFactory: () -> DialogFragment
) {
    if (this == null || isDestroyed) {
        return
    }
    val transaction = beginTransaction()
    transaction.add(dialogFragmentFactory(), tag)
    transaction.commitAllowingStateLoss()
}

fun Fragment.showNetworkErrorToast() {
    toast(getString(R.string.toast_network_error))
}