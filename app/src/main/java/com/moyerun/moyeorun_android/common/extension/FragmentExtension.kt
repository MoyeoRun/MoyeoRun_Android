package com.moyerun.moyeorun_android.common.extension

import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.moyerun.moyeorun_android.R
import com.moyerun.moyeorun_android.common.EventLiveData
import com.moyerun.moyeorun_android.common.EventObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

fun Fragment.repeatOnStart(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

fun <T> Fragment.observeEvent(event: EventLiveData<T>, onEventUnhandledContent: (T) -> Unit) {
    event.observe(viewLifecycleOwner, EventObserver(onEventUnhandledContent))
}