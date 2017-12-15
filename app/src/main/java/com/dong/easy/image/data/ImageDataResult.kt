package com.dong.easy.image.data

import com.google.gson.annotations.SerializedName

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/11/30.
 */
class ImageDataResult {

    @SerializedName("data")
    val data: List<ImageData> = mutableListOf()

    @SerializedName("listNum")
    val listNum: Int = 0
}