package com.moyerun.moyeorun_android.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moyerun.moyeorun_android.common.EventLiveData
import com.moyerun.moyeorun_android.common.MutableEventLiveData
import com.moyerun.moyeorun_android.login.data.LoginRepository
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
            // Todo: 반환 타입 결정 후 분기
            // Todo: Firebase crashlytics userId 세팅
            // Todo: SharedPreference 에 유저 메타데이터 세팅
            val result = loginRepository.signIn(idToken)
            _loginEvent.event = if (result.isNewUser) {
                LoginEvent.NewUser
            } else {
                LoginEvent.RegisteredUser
            }
            _isLoading.value = false
        }
    }
}