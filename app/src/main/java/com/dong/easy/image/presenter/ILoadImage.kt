package com.dong.easy.image.presenter

import com.dong.easy.image.data.ImageDataResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/11/30.
 */
interface ILoadImage {

    /**
     * http://image.baidu.com/search/index?tn=resultjson&word=1080%201920高清壁纸竖屏&pn=10&rn=10
     */
    @GET("search/index")
    fun loadImageList(@QueryMap params: Map<String, String>): Observable<ImageDataResult>

}