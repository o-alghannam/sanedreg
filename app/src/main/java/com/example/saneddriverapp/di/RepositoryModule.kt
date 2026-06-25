package com.example.saneddriverapp.di

import com.example.saneddriverapp.data.remote.api.LookupApi
import com.example.saneddriverapp.data.remote.api.RegistrationApi
import com.example.saneddriverapp.data.repository.LookupRepository
import com.example.saneddriverapp.data.repository.RegistrationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLookupRepository(
        api: LookupApi
    ): LookupRepository {
        return LookupRepository(api)
    }

    @Provides
    @Singleton
    fun provideRegistrationRepository(
        api: RegistrationApi
    ): RegistrationRepository {
        return RegistrationRepository(api)
    }
}