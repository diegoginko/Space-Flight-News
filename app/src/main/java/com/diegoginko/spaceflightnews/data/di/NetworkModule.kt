package com.diegoginko.spaceflightnews.data.di

import com.diegoginko.spaceflightnews.data.remote.SFNApiService
import com.diegoginko.spaceflightnews.data.remote.LaunchLibraryApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "https://api.spaceflightnewsapi.net/v4/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    @Named("SpaceFlightNews")
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        @Named("SpaceFlightNews") retrofit: Retrofit
    ): SFNApiService {
        return retrofit.create(SFNApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("LaunchLibrary")
    fun provideLaunchLibraryRetrofitInstance(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://ll.thespacedevs.com/2.2.0/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideLaunchLibraryApiService(
        @Named("LaunchLibrary") retrofit: Retrofit
    ): LaunchLibraryApiService {
        return retrofit.create(LaunchLibraryApiService::class.java)
    }

}