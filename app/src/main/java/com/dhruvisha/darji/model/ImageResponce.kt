package com.dhruvisha.darji.model

import android.net.Uri


data class ImageResponce(
   val  albumId:Int,
   val id:Int,
   val title: String,
   val url: Uri,
   val thumbnailUrl: Uri
) {
}