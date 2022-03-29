package com.moyerun.moyeorun_android.common.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.moyerun.moyeorun_android.common.extension.getBooleanFromArgument
import com.moyerun.moyeorun_android.common.extension.isActivityDestroyed
import com.moyerun.moyeorun_android.common.extension.putBooleanToArgument

open class BaseDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext()) {
            override fun show() {
                if (context.isActivityDestroyed()) return
                super.show()
            }

            override fun dismiss() {
                if (context.isActivityDestroyed()) return
                super.dismiss()
            }
        }.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val contentView = onCreateView(LayoutInflater.from(context), null, savedInstanceState)
            if (contentView != null) {
                setContentView(contentView)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (isCancelable && getBooleanFromArgument(ARG_DISMISS_ON_PAUSE, false)) {
            dismiss()
        }
    }

    protected fun setDismissOnPause(dismissOnPause: Boolean) {
        putBooleanToArgument(ARG_DISMISS_ON_PAUSE, dismissOnPause)
    }

    companion object {
        private const val ARG_DISMISS_ON_PAUSE = "dismissOnPause"
    }
}