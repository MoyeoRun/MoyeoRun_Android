package com.moyerun.moyeorun_android.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moyerun.moyeorun_android.common.EventLiveData
import com.moyerun.moyeorun_android.common.MutableEventLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val _loginEvent = MutableEventLiveData<LoginEvent>()
    val loginEvent: EventLiveData<LoginEvent>
        get() = _loginEvent

    fun signIn(idToken: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = loginRepository.signIn(idToken)
            if (result is LoginRepository.ApiResponse.Success) {
                // Todo: Firebase crashlytics userId 세팅
                _loginEvent.event = LoginEvent.Success
            } else {
                _loginEvent.event = LoginEvent.Error
            }
            _isLoading.value = false
        }
    }
}