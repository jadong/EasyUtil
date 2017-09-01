package com.dong.easy.base

import com.dong.easy.BuildConfig
import com.dong.easy.constant.AppConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * Created by zengwendong on 2017/6/19.
 */
open class BasePresenter<T> {

    val view:T

    constructor(view: T) {
        this.view = view
    }

    fun buildRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        if (BuildConfig.DEBUG) {//设置debug  Log信息拦截器
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        val retrofit = Retrofit.Builder()
                .baseUrl(AppConstant.SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        return retrofit
    }
}