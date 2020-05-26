package com.grigor.nearme.data.network.sun.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetWorkManager {
    private const val BASE_URL = "https://api.sunrise-sunset.org/"

    private var apiRemoteDataSource: SunRemoteDataSource? = null

    private fun init() {
        apiRemoteDataSource = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(SunRemoteDataSource::class.java)
    }

    fun getApiService(): SunRemoteDataSource {
        if (apiRemoteDataSource == null)
            init()
        return apiRemoteDataSource!!
    }
}