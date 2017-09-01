package com.dong.easy.download

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by zengwendong on 2017/6/20.
 */
interface DownloadApi {

    @GET
    fun downloadFile(@Url url: String): Call<ResponseBody>

}
