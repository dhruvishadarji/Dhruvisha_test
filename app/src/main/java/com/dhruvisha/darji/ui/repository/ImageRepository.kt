package com.dhruvisha.darji.ui.repository



import android.util.Log
import com.dhruvisha.darji.network.ApiService
import com.dhruvisha.darji.room.UserDao
import com.dhruvisha.darji.room.UserData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import io.reactivex.schedulers.Schedulers

class ImageRepository(var api:ApiService,var userDao: UserDao? =null) {
   /* fun getUsers(): Observable<List<UserData>> {
        return Observable.concatArray(
            getUsersFromDb(),
            getUsersFromApi())
    }*/
    fun getUsersFromDb(): Observable<List<UserData>> {
        return api.getImageDetails().filter { it.isNotEmpty() }
            .doOnNext {
                Log.d("","Dispatching ${it.size} users from DB...")
            }
    }
    var compositeDisposable = CompositeDisposable()
    fun getUsersFromApi(): List<UserData> {
        var list:List<UserData> = listOf()
         compositeDisposable.add(
            api.getImageDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("dataa","Received UIModel $it users.")
                    list=it
                    storeUsersInDb(it)
                }, {
                    Log.w("errorrr",it)

                }
                ))
        return list
    }
    fun storeUsersInDb(users: List<UserData>) {
        Observable.fromCallable { userDao?.insertAll(users) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Log.d("","Inserted ${users.size} users from API in DB...")
            }
    }
    val getAllUserData: Observable<List<UserData>> = api.getImageDetails().subscribeOn(Schedulers.io())

}