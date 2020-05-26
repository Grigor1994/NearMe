package com.grigor.nearme.data.repository

import com.grigor.nearme.data.network.sun.api.SunRemoteDataSource
import com.grigor.nearme.data.network.sun.api.SunResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//interface SunRepository {
//    suspend fun getSunData()
//}

class SunDataRepository(private val sunRemoteDataSource: SunRemoteDataSource) {

     fun getSunData(lat:String, lng:String, date:String, onSuccess: (SunResponse)->Unit) {
         sunRemoteDataSource.getSunData(lat , lng, date)
             .enqueue(object : Callback<SunResponse> {
                 override fun onResponse(call: Call<SunResponse>, response: Response<SunResponse>) {
                if (response.code() == 200) {
                    onSuccess(response.body()!!)
                }
                 }
                 override fun onFailure(call: retrofit2.Call<SunResponse>, t: Throwable) {
                 }
             })
     }
 }





