package com.moyerun.moyeorun_android.common.extension

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.moyerun.moyeorun_android.R
import com.moyerun.moyeorun_android.common.EventLiveData
import com.moyerun.moyeorun_android.common.EventObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Activity.toast(msg: String, isShort: Boolean = true) {
    Toast.makeText(this, msg, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
}

inline fun FragmentActivity.showAllowingStateLoss(
    tag: String?,
    dialogFragmentFactory: () -> DialogFragment
) {
    supportFragmentManager.showAllowingStateLoss(tag, dialogFragmentFactory)
}

fun Activity.showNetworkErrorToast() {
    toast(getString(R.string.toast_network_error))
}

fun ComponentActivity.repeatOnStart(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch { repeatOnLifecycle(Lifecycle.State.STARTED, block) }
}

fun <T> ComponentActivity.observeEvent(
    event: EventLiveData<T>,
    onEventUnhandledContent: (T) -> Unit
) {
    event.observe(this, EventObserver(onEventUnhandledContent))
}