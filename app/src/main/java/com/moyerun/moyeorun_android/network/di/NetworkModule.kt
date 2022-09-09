package com.moyerun.moyeorun_android.network.di

import com.moyerun.moyeorun_android.BuildConfig
import com.moyerun.moyeorun_android.network.MoyeorunJsonConverterFactory
import com.moyerun.moyeorun_android.network.MoyeorunNetworkDataSource
import com.moyerun.moyeorun_android.network.MoyeorunNetworkDataSourceImpl
import com.moyerun.moyeorun_android.network.MoyeorunService
import com.moyerun.moyeorun_android.network.calladapter.ApiResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

private const val BASE_URL = BuildConfig.BASE_URL

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(ApiResultCallAdapterFactory())
            .addConverterFactory(MoyeorunJsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesMoyeorunNetworkDataSource(retrofit: Retrofit): MoyeorunNetworkDataSource {
        return MoyeorunNetworkDataSourceImpl(retrofit.create(MoyeorunService::class.java))
    }
}