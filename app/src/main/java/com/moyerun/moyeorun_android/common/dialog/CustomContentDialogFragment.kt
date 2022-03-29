package com.moyerun.moyeorun_android.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moyerun.moyeorun_android.R
import com.moyerun.moyeorun_android.common.extension.getStringFromArgument
import com.moyerun.moyeorun_android.common.extension.putStringToArgument
import com.moyerun.moyeorun_android.databinding.DialogCustomContentBinding

abstract class CustomContentDialogFragment : BaseDialogFragment() {

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DialogCustomContentBinding.inflate(inflater, container, false).apply {
            contentCustomDialog.addView(
                onCreateContentView(
                    inflater,
                    contentCustomDialog,
                    savedInstanceState
                )
            )
        }.root
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DialogCustomContentBinding.bind(view)
        binding.headerTextDialog.textDialogHeader.text = getStringFromArgument(ARG_TITLE)
        with(binding.footerTextDialog) {
            buttonNegativeDialogFooter.text =
                getStringFromArgument(ARG_LABEL_NEGATIVE, getString(R.string.cancel))
            buttonPositiveDialogFooter.text =
                getStringFromArgument(ARG_LABEL_POSITIVE, getString(R.string.ok))

            buttonNegativeDialogFooter.setOnClickListener {
                onNegativeClick()
                dismissAllowingStateLoss()
            }

            buttonPositiveDialogFooter.setOnClickListener {
                onPositiveClick()
                dismissAllowingStateLoss()
            }
        }
        onContentViewCreated(view, savedInstanceState)
    }

    abstract fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    abstract fun onContentViewCreated(view: View, savedInstanceState: Bundle?)

    protected open fun onPositiveClick() { }

    protected open fun onNegativeClick() { }

    protected fun setTitle(title: String?) {
        putStringToArgument(ARG_TITLE, title)
    }

    protected fun setPositiveLabel(label: String?) {
        putStringToArgument(ARG_LABEL_POSITIVE, label)
    }

    protected fun setNegativeLabel(label: String?) {
        putStringToArgument(ARG_LABEL_NEGATIVE, label)
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_LABEL_NEGATIVE = "negativeLabel"
        private const val ARG_LABEL_POSITIVE = "positiveLabel"
    }
}