package com.moyerun.moyeorun_android.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moyerun.moyeorun_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoginGoogle.setOnClickListener {
            //Todo: 구글 로그인 작업 @winter223
        }
    }
}