package com.dhruvisha.darji.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhruvisha.darji.model.ImageResponce
import com.dhruvisha.darji.network.ApiService
import com.dhruvisha.darji.repository.ImageRepository
import com.dhruvisha.darji.room.UserData
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

class ImageViewModel():ViewModel() {
    private val repository = ImageRepository(ApiService.invoke())
    private val userDataList = MutableLiveData<List<UserData>>()

    private val networkCallSuccess = MutableLiveData<Boolean>()

    fun executeNetworkCall() =
        repository.getAllUserData
            .subscribeBy(
                onSuccess = { userData ->
                    userDataList.postValue(userData)
                    networkCallSuccess.postValue(true)
                },
                onError = { networkCallSuccess.postValue(false) })

    fun getNetworkCallSuccess(): LiveData<Boolean> = networkCallSuccess

    fun getNetworkCallResults(): LiveData<List<UserData>> = userDataList
}