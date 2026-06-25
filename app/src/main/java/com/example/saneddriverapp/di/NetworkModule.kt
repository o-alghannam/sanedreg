package com.example.saneddriverapp.di

import com.example.saneddriverapp.data.remote.api.LookupApi
import com.example.saneddriverapp.data.remote.api.RegistrationApi
import com.example.saneddriverapp.data.remote.api.ReviewApi
import com.example.saneddriverapp.data.remote.api.UploadApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL =
        "https://gateway.dev.svc.saned.io/"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {

        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideLookupApi(
        retrofit: Retrofit
    ): LookupApi {

        return retrofit.create(LookupApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRegistrationApi(
        retrofit: Retrofit
    ): RegistrationApi {

        return retrofit.create(RegistrationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUploadApi(
        retrofit: Retrofit
    ): UploadApi {

        return retrofit.create(UploadApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReviewApi(
        retrofit: Retrofit
    ): ReviewApi {

        return retrofit.create(ReviewApi::class.java)
    }
}