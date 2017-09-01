package com.dong.easy.api

import com.google.gson.annotations.SerializedName

/**
 *
 * Created by zengwendong on 2017/6/17.
 */
data class DataResult<out T> constructor(@SerializedName("data") val data:T?,
                                         @SerializedName("code") val code:Int,
                                         @SerializedName("msg") val msg:String)