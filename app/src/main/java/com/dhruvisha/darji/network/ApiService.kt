package com.dhruvisha.darji.network


import com.dhruvisha.darji.model.ImageResponce
import com.dhruvisha.darji.room.UserData
import io.reactivex.Observable

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET(API)
    fun getImageDetails(): Single<List<UserData>>

    companion object {

        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
        const val API = "photos"

        private var api: ApiService? = null

        operator fun invoke(): ApiService {

            if (api == null) {
                api = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }
            return api!!
        }
    }
}