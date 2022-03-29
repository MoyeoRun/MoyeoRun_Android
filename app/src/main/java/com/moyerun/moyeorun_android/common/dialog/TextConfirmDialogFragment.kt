package com.moyerun.moyeorun_android.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moyerun.moyeorun_android.common.extension.getStringFromArgument
import com.moyerun.moyeorun_android.common.extension.putStringToArgument
import com.moyerun.moyeorun_android.databinding.DialogContentTextBinding

class TextConfirmDialogFragment : CustomContentDialogFragment() {

    private var onPositiveClick: () -> Unit = {}
    private var onNegativeClick: () -> Unit = {}

    override fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DialogContentTextBinding.inflate(inflater, container, false).root
    }

    override fun onContentViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = DialogContentTextBinding.bind(view)
        binding.textDialogContent.text = getStringFromArgument(ARG_CONTENT)
    }

    override fun onPositiveClick() {
        onPositiveClick.invoke()
    }

    override fun onNegativeClick() {
        onNegativeClick.invoke()
    }

    companion object {
        private const val ARG_CONTENT = "content"

        fun newInstance(
            title: String,
            content: String,
            positiveLabel: String? = null,
            negativeLabel: String? = null,
            dismissOnPause: Boolean = false,
            isCancelable: Boolean = false,
            onPositiveClick: () -> Unit = {},
            onNegativeClick: () -> Unit = {}
        ): TextConfirmDialogFragment = TextConfirmDialogFragment().apply {
            putStringToArgument(ARG_CONTENT, content)
            setTitle(title)
            setPositiveLabel(positiveLabel)
            setNegativeLabel(negativeLabel)
            setDismissOnPause(dismissOnPause)
            this.isCancelable = isCancelable
            this.onPositiveClick = onPositiveClick
            this.onNegativeClick = onNegativeClick
        }
    }
}