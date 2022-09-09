package com.moyerun.moyeorun_android.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moyerun.moyeorun_android.common.EventLiveData
import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.common.MutableEventLiveData
import com.moyerun.moyeorun_android.login.ProviderType
import com.moyerun.moyeorun_android.login.SignUpMetaData
import com.moyerun.moyeorun_android.login.data.AuthRepository
import com.moyerun.moyeorun_android.network.calladapter.onFailure
import com.moyerun.moyeorun_android.network.calladapter.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val _loginEvent = MutableEventLiveData<LoginEvent>()
    val loginEvent: EventLiveData<LoginEvent>
        get() = _loginEvent

    fun googleSignIn(idToken: String) {
        signInInternal(idToken, ProviderType.GOOGLE)
    }

    private fun signInInternal(idToken: String, providerType: ProviderType) {
        viewModelScope.launch {
            _isLoading.value = true
            authRepository.signIn(idToken, providerType)
                .onSuccess {
                    _loginEvent.event = if (it.isNewUser) {
                        LoginEvent.NewUser(SignUpMetaData(idToken, providerType))
                    } else {
                        LoginEvent.RegisteredUser
                    }
                }.onFailure {
                    Lg.fe(it)
                    _loginEvent.event = LoginEvent.Error
                }
            _isLoading.value = false
        }
    }
}