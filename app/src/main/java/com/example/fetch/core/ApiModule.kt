package com.example.fetch.core

import com.example.fetch.network.ApiHelper
import com.example.fetch.network.ApiHelperImpl
import com.example.fetch.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideBaseUrl(): String = "https://fetch-hiring.s3.amazonaws.com"

    @Provides
    fun provideClient(): OkHttpClient = OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideApiHelper(apiService: ApiService): ApiHelper = ApiHelperImpl(apiService)
}
