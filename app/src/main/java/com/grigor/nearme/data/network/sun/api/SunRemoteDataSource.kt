package com.grigor.nearme.data.network.sun.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SunRemoteDataSource {

    @GET("json")
     fun getSunData(
        @Query("lat") lat: String, @Query("lng") lng: String, @Query("date") date: String
    ): Call<SunResponse>
}