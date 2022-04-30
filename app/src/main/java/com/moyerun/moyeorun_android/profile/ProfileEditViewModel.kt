package com.moyerun.moyeorun_android.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.moyerun.moyeorun_android.common.EventLiveData
import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.common.MutableEventLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProfileEditViewModel: ViewModel() {

    private val _profileUiModel = MutableStateFlow(ProfileUiModel())
    val profileUiModel: StateFlow<ProfileUiModel>
        get() = _profileUiModel

    private val _profileEvent = MutableEventLiveData<ProfileEvent>()
    val profileEvent: EventLiveData<ProfileEvent>
        get() = _profileEvent

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
                _profileEvent.event = ProfileEvent.Error(ProfileError.WRONG_ACCESS)
            }
        }
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

    fun onImageUrlChanged(imageUri: Uri) {
        _profileUiModel.update {
            it.copy(imageUri = imageUri)
        }
    }

    fun signUp() {
        // Todo: 회원가입 API 호출
        Lg.d("SignUp : ${profileUiModel.value}, $signUpMetaData")
        _profileEvent.event = ProfileEvent.SuccessSignUp
    }
}