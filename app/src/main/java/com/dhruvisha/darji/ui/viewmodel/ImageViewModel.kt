package com.dhruvisha.darji.ui.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhruvisha.darji.network.ApiService
import com.dhruvisha.darji.ui.repository.ImageRepository
import com.dhruvisha.darji.room.UserData
import io.reactivex.rxkotlin.subscribeBy

class ImageViewModel():ViewModel() {
    private val repository =
        ImageRepository(ApiService.invoke())
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