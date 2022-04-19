package com.moyerun.moyeorun_android.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.moyerun.moyeorun_android.R
import com.moyerun.moyeorun_android.common.extension.repeatOnStart
import com.moyerun.moyeorun_android.common.extension.setDrawableEnd
import com.moyerun.moyeorun_android.common.extension.setTextIfNew
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

        val originalProfile: ProfileUiModel? = intent.getParcelableExtra(EXTRA_PROFILE_UI_MODEL)
        val isNewProfile = originalProfile == null

        if (isNewProfile) {
            binding.textviewProfileTitle.text = "기본 정보"
            binding.buttonProfileConfirm.text = "다음"
        } else {
            binding.textviewProfileTitle.text = "프로필"
            binding.buttonProfileConfirm.text = "완료"
        }

        viewModel.updateProfile(originalProfile)

        binding.edittextProfileName.doAfterTextChanged {
            viewModel.onNameChanged(it?.toString().orEmpty())
        }

        binding.edittextProfileNickname.doAfterTextChanged {
            viewModel.onNicknameChanged(it?.toString().orEmpty())
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

        fun startActivity(context: Context, profileUiModel: ProfileUiModel? = null) {
            context.startActivity(Intent(context, ProfileEditActivity::class.java).apply {
                putExtra(EXTRA_PROFILE_UI_MODEL, profileUiModel)
            })
        }
    }
}