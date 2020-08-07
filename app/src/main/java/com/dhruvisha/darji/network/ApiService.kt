package com.dhruvisha.darji.network


import com.dhruvisha.darji.room.UserData
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("photos")
    fun getImageDetails(): Observable<List<UserData>>
}