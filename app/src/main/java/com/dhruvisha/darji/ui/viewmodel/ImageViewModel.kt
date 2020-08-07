package com.dhruvisha.darji.ui.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhruvisha.darji.model.ImageResponce
import com.dhruvisha.darji.network.ApiService
import com.dhruvisha.darji.room.UserDao
import com.dhruvisha.darji.ui.repository.ImageRepository
import com.dhruvisha.darji.room.UserData
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

class ImageViewModel(private val userRepository: ImageRepository):ViewModel() {
   /* private val repository =
        ImageRepository(ApiService.invoke())*/
    private  var userDataList : List<UserData> = arrayListOf<UserData>()

    private val networkCallSuccess = MutableLiveData<Boolean>()
    /*fun getUsers(): Observable<Unit>? {

        //Create the data for your UI, the users lists and maybe some additional data needed as well
        return userRepository.getUsers()
            .map {
                Log.d("","Mapping users to UIData...")

                userDataList=it

            }
            .onErrorReturn {
                Log.d("","An error occurred $")
                userDataList=emptyList()
            }
    }*/

}