package com.dhruvisha.darji.room

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData (@PrimaryKey val id: Int,
                     @PrimaryKey val albumId: Int,
                     @ColumnInfo(name = "title") val title: String?,
                     @ColumnInfo(name = "url") val url: Uri?,
                     @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: Uri?

){




}