package com.dhruvisha.darji.ui.repository



import com.dhruvisha.darji.network.ApiService
import com.dhruvisha.darji.room.UserData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ImageRepository(var api:ApiService) {
    val getAllUserData: Single<List<UserData>> = api.getImageDetails().subscribeOn(Schedulers.io())

}