package com.dong.easy.base

import android.util.Log
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

    val view: T

    constructor(view: T) {
        this.view = view
    }

    fun buildRetrofit(baseUrl: String = AppConstant.SERVER_BASE_URL): Retrofit {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {//设置debug  Log信息拦截器
            val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.i("RetrofitLog", message)
            })
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        val okHttpClient = builder.build()
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }


}