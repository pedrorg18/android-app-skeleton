package com.pedroroig.appskeleton.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGithubNetwork {

    private const val BASE_URL = "https://api.github.com/"

    private val logging = HttpLoggingInterceptor().apply {
        setLevel(Level.BODY)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val networkApi: GithubNetworkApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubNetworkApi::class.java)
    }


}
