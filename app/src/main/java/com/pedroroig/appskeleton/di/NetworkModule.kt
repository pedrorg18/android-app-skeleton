package com.pedroroig.appskeleton.di

import com.pedroroig.appskeleton.data.GithubDao
import com.pedroroig.appskeleton.data.network.GithubNetworkApi
import com.pedroroig.appskeleton.data.network.GithubNetworkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.github.com/"

    private val logging = HttpLoggingInterceptor().apply {
        setLevel(Level.BODY)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    @Provides
    fun provideGithubNetworkApi(): GithubNetworkApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubNetworkApi::class.java)

    @Provides
    fun provideGithubDao(apiService: GithubNetworkApi): GithubDao =
        GithubNetworkDao(apiService)

}