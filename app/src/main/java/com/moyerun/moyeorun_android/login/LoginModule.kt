package com.moyerun.moyeorun_android.login

import com.moyerun.moyeorun_android.login.data.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class LoginModule {

    @Provides
    fun providesLoginRepository(): LoginRepository = LoginRepository()
}