package com.moyerun.moyeorun_android.login.data

import com.moyerun.moyeorun_android.login.data.impl.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindsAuthRepository(repository: AuthRepositoryImpl): AuthRepository
}