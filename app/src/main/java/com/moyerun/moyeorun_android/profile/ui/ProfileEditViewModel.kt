package com.moyerun.moyeorun_android.profile.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moyerun.moyeorun_android.common.EventLiveData
import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.common.MutableEventLiveData
import com.moyerun.moyeorun_android.login.data.AuthRepository
import com.moyerun.moyeorun_android.login.SignUpMetaData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _profileUiModel = MutableStateFlow(ProfileUiModel())
    val profileUiModel: StateFlow<ProfileUiModel>
        get() = _profileUiModel

    private val _profileEvent = MutableEventLiveData<ProfileEvent>()
    val profileEvent: EventLiveData<ProfileEvent>
        get() = _profileEvent

    private val _profileErrorEvent = MutableEventLiveData<ProfileError>()
    val profileErrorEvent: EventLiveData<ProfileError>
        get() = _profileErrorEvent

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean>
        get() = _isButtonEnabled

    private var signUpMetaData: SignUpMetaData? = null
    private var oldProfileUiModel: ProfileUiModel? = null

    private var isNewProfile = true

    fun updateData(signUpMetaData: SignUpMetaData?, profileUiModel: ProfileUiModel?) {
        when {
            signUpMetaData == null && profileUiModel != null -> {
                _profileUiModel.update { profileUiModel }
                oldProfileUiModel = profileUiModel
                isNewProfile = false
            }
            signUpMetaData != null && profileUiModel == null -> {
                this.signUpMetaData = signUpMetaData
                isNewProfile = true
            }
            else -> {
                Lg.fw("Wrong access. signUpMetadata: $signUpMetaData, originProfile: $profileUiModel")
                _profileErrorEvent.event = ProfileError.WRONG_ACCESS
            }
        }
    }

    fun onNameChanged(name: String) = updateWithValidate {
        _profileUiModel.update {
            it.copy(name = name)
        }
        _profileUiModel.value
    }

    fun onNicknameChanged(nickname: String) = updateWithValidate {
        _profileUiModel.update {
            it.copy(nickname = nickname)
        }
        _profileUiModel.value
    }

    fun onImageUrlChanged(imageUri: Uri) {
        _profileUiModel.update {
            it.copy(imageUri = imageUri)
        }
    }

    fun onGenderChanged(gender: Gender) = updateWithValidate {
        _profileUiModel.update {
            it.copy(gender = gender)
        }
        _profileUiModel.value
    }

    fun signUp() {
        val metaData = signUpMetaData
        if (metaData == null) {
            Lg.fw("SignUp Failure. meta data is null")
            _profileErrorEvent.event = ProfileError.UNKNOWN
            return
        }
        viewModelScope.launch {
            authRepository.signUp(profileUiModel.value.toSignUpRequest(metaData))
            //Todo: ApiResult 사용
            _profileEvent.event = ProfileEvent.SUCCESS_SIGN_UP
        }
    }

    private fun updateWithValidate(updateAction: () -> ProfileUiModel) {
        val updatedProfileUiModel = updateAction.invoke()
        _isButtonEnabled.update {
            updatedProfileUiModel.validate()
        }
    }

    private fun ProfileUiModel.validate(): Boolean {
        return name.validateName()
                && nickname.validateNickname()
                && gender != Gender.NONE
    }

    private fun String.validateName(): Boolean {
        return isNotEmpty()
    }

    private fun String.validateNickname(): Boolean {
        return isNotEmpty()
    }
}