package com.moyerun.moyeorun_android.common.extension

import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.DrawableRes

fun View.setOnDebounceClickListener(interval: Long = 1000L, action: (View?) -> Unit) {
    val debounceClickListener = object : View.OnClickListener {
        private var lastClickedMillis = 0L

        override fun onClick(view: View?) {
            val now = System.currentTimeMillis()
            if (now - lastClickedMillis < interval) {
                return
            }
            lastClickedMillis = now
            action.invoke(view)
        }
    }
    setOnClickListener(debounceClickListener)
}

fun TextView.setTextIfNew(text: CharSequence?) {
    if (TextUtils.equals(this.text, text).not()) {
        setText(text)
    }
}

fun RadioButton.setCheckIfNew(check: Boolean) {
    val oldValue = isChecked
    if (oldValue != check) {
        isChecked = check
    }
}