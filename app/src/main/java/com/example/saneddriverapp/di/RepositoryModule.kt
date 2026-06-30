package com.example.saneddriverapp.di

import com.example.saneddriverapp.data.repository.LookupRepositoryImpl
import com.example.saneddriverapp.data.repository.RegistrationRepositoryImpl
import com.example.saneddriverapp.data.repository.ReviewRepositoryImpl
import com.example.saneddriverapp.domain.repository.LookupRepository
import com.example.saneddriverapp.domain.repository.RegistrationRepository
import com.example.saneddriverapp.domain.repository.ReviewRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRegistrationRepository(
        impl: RegistrationRepositoryImpl
    ): RegistrationRepository

    @Binds
    @Singleton
    abstract fun bindReviewRepository(
        impl: ReviewRepositoryImpl
    ): ReviewRepository

    @Binds
    @Singleton
    abstract fun bindLookupRepository(
        impl: LookupRepositoryImpl
    ): LookupRepository
}