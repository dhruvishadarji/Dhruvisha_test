package com.dhruvisha.darji.model

import android.net.Uri
import com.dhruvisha.darji.room.UserDao
import com.dhruvisha.darji.room.UserData


data class ImageResponce(val users: List<UserData>, val message: String, val error: Throwable? = null) {
}