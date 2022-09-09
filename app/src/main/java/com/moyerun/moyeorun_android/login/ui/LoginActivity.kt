package com.moyerun.moyeorun_android.login.ui

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.moyerun.moyeorun_android.BuildConfig
import com.moyerun.moyeorun_android.MainActivity
import com.moyerun.moyeorun_android.R
import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.common.extension.observeEvent
import com.moyerun.moyeorun_android.common.extension.showNetworkErrorToast
import com.moyerun.moyeorun_android.common.extension.toast
import com.moyerun.moyeorun_android.databinding.ActivityLoginBinding
import com.moyerun.moyeorun_android.profile.ui.ProfileEditActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    private val oneTapClient: SignInClient by lazy { Identity.getSignInClient(this) }
    private val signInRequest: BeginSignInRequest by lazy { getBeginSignInRequest() }

    private val beginSignInResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result != null) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    if (idToken != null) {
                        Lg.i("Success. token : $idToken")
                        viewModel.googleSignIn(idToken)
                    } else {
                        showUnknownErrorToast()
                        Lg.fe("No ID token")
                    }
                } catch (e: ApiException) {
                    when (e.statusCode) {
                        CommonStatusCodes.CANCELED -> { /*Doing nothing*/ }
                        CommonStatusCodes.NETWORK_ERROR -> {
                            showNetworkErrorToast()
                            Lg.e("One-tap encountered a network error. $e")
                        }
                        else -> {
                            showUnknownErrorToast()
                            Lg.fe("Couldn't get credential from result.", e)
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeEvent(viewModel.loginEvent) { event ->
            when (event) {
                is LoginEvent.RegisteredUser -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is LoginEvent.NewUser -> {
                    ProfileEditActivity.startActivity(this, event.signUpMetaData)
                }
                is LoginEvent.Error -> {
                    showUnknownErrorToast()
                }
            }
        }

        binding.buttonLoginGoogle.setOnClickListener {
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this) { result ->
                    try {
                        val intentSenderRequest =
                            IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                        beginSignInResultLauncher.launch(intentSenderRequest)
                    } catch (e: IntentSender.SendIntentException) {
                        showUnknownErrorToast()
                        Lg.fe("Couldn't start One Tab UI", e)
                    }
                }
                .addOnFailureListener(this) {
                    // 기기에 등록된 계정이 없는 경우 호출
                    startDeviceGoogleSignInActivity()
                    // 간혹 등록된 계정이 있는데도 해당 콜백을 타는 경우가 있어서 로깅
                    Lg.fe("No Google Accounts found", it)
                }
        }
    }

    private fun startDeviceGoogleSignInActivity() {
        startActivity(Intent(Settings.ACTION_ADD_ACCOUNT).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
        })
    }

    private fun getBeginSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    // false 로 설정해서 앱에 로그인한 적이 있는 계정뿐만 아니라
                    // 기기에 등록된 구글 계정을 모두 보여준다
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            // 하나의 계정만 있다면 자동으로 선택
            .setAutoSelectEnabled(true)
            .build()
    }

    private fun showUnknownErrorToast() {
        toast(getString(R.string.login_toast_unknown_error))
    }
}