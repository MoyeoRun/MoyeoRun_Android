package com.moyerun.moyeorun_android.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProfileEditViewModel: ViewModel() {

    private val _profileUiModel = MutableStateFlow(ProfileUiModel())
    val profileUiModel: StateFlow<ProfileUiModel>
        get() = _profileUiModel

    private var oldProfileUiModel: ProfileUiModel? = null

    private var isNewPost = true

    fun updateProfile(profileUiModel: ProfileUiModel?) {
        if (profileUiModel == null) return
        _profileUiModel.update { profileUiModel }
        oldProfileUiModel = profileUiModel
        isNewPost = false
    }

    fun onNameChanged(name: String) {
        _profileUiModel.update {
            it.copy(name = name)
        }
    }

    fun onNicknameChanged(nickname: String) {
        _profileUiModel.update {
            it.copy(nickname = nickname)
        }
    }

    fun onImageUrlChanged(imageUrl: String) {
        _profileUiModel.update {
            it.copy(imageUrl = imageUrl)
        }
    }
}