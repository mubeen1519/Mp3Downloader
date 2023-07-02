package com.pawxy.mp3downloader.di

import com.pawxy.mp3downloader.model.data.StepsRepository
import com.pawxy.mp3downloader.model.data.StepsRepositoryImplementation
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }
}

@Module
@InstallIn(ViewModelComponent :: class)
abstract class ServiceModule {
    @Binds
    abstract fun provideStepService(impl: StepsRepositoryImplementation) : StepsRepository
}