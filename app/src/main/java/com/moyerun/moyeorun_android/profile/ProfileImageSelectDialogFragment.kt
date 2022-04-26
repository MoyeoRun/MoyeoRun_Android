package com.moyerun.moyeorun_android.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moyerun.moyeorun_android.common.dialog.RoundDialogFragment
import com.moyerun.moyeorun_android.common.extension.setOnDebounceClickListener
import com.moyerun.moyeorun_android.databinding.DialogProfileImageSelectBinding

class ProfileImageSelectDialogFragment : RoundDialogFragment() {

    private var onGalleryClick: () -> Unit = {}
    private var onDefaultImagesClick: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DialogProfileImageSelectBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DialogProfileImageSelectBinding.bind(view)

        binding.buttonImageSelectGallery.setOnDebounceClickListener {
            onGalleryClick.invoke()
            dismissAllowingStateLoss()
        }

        binding.buttonImageSelectDefault.setOnDebounceClickListener {
            onDefaultImagesClick.invoke()
            dismissAllowingStateLoss()
        }
    }

    companion object {
        fun getInstance(
            onGalleryClick: () -> Unit,
            onDefaultImagesClick: () -> Unit
        ): ProfileImageSelectDialogFragment {
            return ProfileImageSelectDialogFragment().apply {
                this.onGalleryClick = onGalleryClick
                this.onDefaultImagesClick = onDefaultImagesClick
            }
        }
    }
}