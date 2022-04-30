package com.moyerun.moyeorun_android.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.moyerun.moyeorun_android.R
import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.common.extension.*
import com.moyerun.moyeorun_android.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileEditActivity : AppCompatActivity() {

    private val viewModel: ProfileEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signUpMetaData: SignUpMetaData? = intent.getParcelableExtra(EXTRA_SIGN_UP_META_DATA)
        val originalProfile: ProfileUiModel? = intent.getParcelableExtra(EXTRA_PROFILE_UI_MODEL)
        val isNewProfile = (originalProfile == null && signUpMetaData != null)

        if (isNewProfile) {
            binding.textviewProfileTitle.text = "기본 정보"
            binding.buttonProfileConfirm.text = "다음"
        } else {
            binding.textviewProfileTitle.text = "프로필"
            binding.buttonProfileConfirm.text = "완료"
        }

        viewModel.updateData(signUpMetaData, originalProfile)

        binding.edittextProfileName.doAfterTextChanged {
            viewModel.onNameChanged(it?.toString().orEmpty())
        }

        binding.edittextProfileNickname.doAfterTextChanged {
            viewModel.onNicknameChanged(it?.toString().orEmpty())
        }

        binding.badgeimageviewProfileImage.setOnDebounceClickListener {
            showAllowingStateLoss("selectImage") {
                ProfileImageSelectDialogFragment.getInstance(
                    onGalleryClick = {
                        // Todo : 갤러리 선택 화면
                    },
                    onDefaultImagesClick = {
                        // Todo : 기본 이미지 선택 화면
                    }
                )
            }
        }

        binding.buttonProfileConfirm.setOnDebounceClickListener {
            if (isNewProfile) {
                viewModel.signUp()
            }
        }

        repeatOnStart {
            launch {
                viewModel.profileUiModel
                    .map { it.name }
                    .distinctUntilChanged()
                    .collect {
                        binding.edittextProfileName.setTextAndCheckIcon(it)
                    }
            }
            launch {
                viewModel.profileUiModel
                    .map { it.nickname }
                    .distinctUntilChanged()
                    .collect {
                        binding.edittextProfileNickname.setTextAndCheckIcon(it)
                    }
            }
            launch {
                viewModel.profileUiModel
                    .map { it.imageUrl }
                    .distinctUntilChanged()
                    .collect {
                        binding.badgeimageviewProfileImage.setBigCircleImgSrc(it)
                    }
            }
        }

        observeEvent(viewModel.profileEvent) {
            when (it) {
                is ProfileEvent.SuccessSignUp -> {
                    // Todo: 환영 액티비티로 이동
                    Lg.d("observeEvent : Go to welcome activity!")
                }
                is ProfileEvent.Error -> {
                    when (it.error) {
                        ProfileError.WRONG_ACCESS -> {
                            Lg.fw("Wrong access. signUpMetadata: $signUpMetaData, originProfile: $originalProfile")
                            toast("잘못된 접근입니다.")
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun isValidText(text: String): Boolean {
        // Todo: 유효성 검사 조건 추가 (ex. 정규 표현식, 글자 제한)
        return text.isNotEmpty()
    }

    private fun EditText.setTextAndCheckIcon(text: String) {
        val isValid = isValidText(text)
        val resId = if (isValid) {
            R.drawable.ic_check
        } else {
            null
        }
        setDrawableEnd(resId)
        setTextIfNew(text)
    }

    companion object {
        private const val EXTRA_PROFILE_UI_MODEL = "profileUiModel"
        private const val EXTRA_SIGN_UP_META_DATA = "signUpMetaData"

        // 프로필 수정 시 사용
        fun startActivity(context: Context, profileUiModel: ProfileUiModel) {
            context.startActivity(Intent(context, ProfileEditActivity::class.java).apply {
                putExtra(EXTRA_PROFILE_UI_MODEL, profileUiModel)
            })
        }

        // 회원가입 시 사용
        fun startActivity(context: Context, signUpMetaData: SignUpMetaData) {
            context.startActivity(Intent(context, ProfileEditActivity::class.java).apply {
                putExtra(EXTRA_SIGN_UP_META_DATA, signUpMetaData)
            })
        }
    }
}