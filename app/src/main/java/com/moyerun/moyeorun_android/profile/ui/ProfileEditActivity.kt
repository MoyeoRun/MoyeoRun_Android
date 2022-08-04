package com.moyerun.moyeorun_android.profile.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.moyerun.moyeorun_android.R
import com.moyerun.moyeorun_android.common.Lg
import com.moyerun.moyeorun_android.common.extension.*
import com.moyerun.moyeorun_android.databinding.ActivityProfileBinding
import com.moyerun.moyeorun_android.login.SignUpMetaData
import dagger.hilt.android.AndroidEntryPoint
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

        val galleryLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
                if (imageUri != null) {
                    viewModel.onImageUrlChanged(imageUri)
                } else {
                    Lg.fw("Cannot get Image Uri from gallery")
                }
            }

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
                        galleryLauncher.launch("image/*")
                    },
                    onDefaultImagesClick = {
                        // Todo : 기본 이미지 선택 화면
                    }
                )
            }
        }

        binding.radiogroupProfileGender.setOnCheckedChangeListener { _, checkedId ->
            val gender = when (checkedId) {
                R.id.radiobutton_profile_man -> Gender.MALE
                R.id.radiobutton_profile_woman -> Gender.FEMALE
                else -> Gender.NONE
            }
            viewModel.onGenderChanged(gender)
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
                        binding.edittextProfileName.setTextIfNew(it)
                    }
            }
            launch {
                viewModel.profileUiModel
                    .map { it.nickname }
                    .distinctUntilChanged()
                    .collect {
                        binding.edittextProfileNickname.setTextIfNew(it)
                    }
            }
            launch {
                viewModel.profileUiModel
                    .map { it.imageUri }
                    .distinctUntilChanged()
                    .collect {
                        binding.badgeimageviewProfileImage.setBigCircleImgSrc(it)
                    }
            }
            launch {
                viewModel.profileUiModel
                    .map { it.gender }
                    .distinctUntilChanged()
                    .collect {
                        when (it) {
                            Gender.MALE -> binding.radiobuttonProfileMan.setCheckIfNew(true)
                            Gender.FEMALE -> binding.radiobuttonProfileWoman.setCheckIfNew(true)
                            Gender.NONE -> binding.radiogroupProfileGender.clearCheck()
                        }
                    }
            }
            launch {
                viewModel.isButtonEnabled
                    .collect { buttonEnabled ->
                        binding.buttonProfileConfirm.isEnabled = buttonEnabled
                    }
            }
        }

        observeEvent(viewModel.profileEvent) {
            when (it) {
                ProfileEvent.SUCCESS_SIGN_UP -> {
                    // Todo: 환영 액티비티로 이동
                    Lg.d("observeEvent : Go to welcome activity!")
                }
            }
        }

        observeEvent(viewModel.profileErrorEvent) {
            when (it) {
                ProfileError.DUPLICATE_NICKNAME -> {
                    binding.alertDuplicate()
                }
                ProfileError.WRONG_ACCESS -> {
                    toast(getString(R.string.profile_toast_wrong_access))
                    finish()
                }
                ProfileError.UNKNOWN, ProfileError.UNKNOWN_AUTH -> {
                    toast(getString(R.string.profile_toast_unknown_error))
                }
                ProfileError.NETWORK -> {
                    showNetworkErrorToast()
                }
            }
        }
    }

    private fun ActivityProfileBinding.alertDuplicate() {
        edittextProfileNickname.setTextAppearance(R.style.Profile_Input_Wrong)
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