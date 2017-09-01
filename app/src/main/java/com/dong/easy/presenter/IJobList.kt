package com.dong.easy.presenter

import com.dong.easy.api.DataResult
import com.dong.easy.model.Demo
import io.reactivex.Observable
import retrofit2.http.GET

/**
 *
 * Created by zengwendong on 2017/6/17.
 */
interface IJobList {

    @GET("job/list/")
    fun getJobList(): Observable<DataResult<Demo>>

}