package com.dong.easy.image.presenter

import android.util.Log
import com.dong.easy.BuildConfig
import com.dong.easy.base.BasePresenter
import com.dong.easy.constant.AppConstant
import com.dong.easy.image.view.IImageLoadView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * Created by zengwendong on 2017/6/17.
 */
class LoadImagePresenter {

    private val pageSize = 30
    private var view: IImageLoadView

    constructor(view: IImageLoadView) {
        this.view = view
    }

    fun loadImageList(page: Int) {
        val params = mutableMapOf<String, String>()
        params["word"] = view.getSearchKeyword()
        params["tn"] = "resultjson"
        params["pn"] = "" + ((page - 1) * pageSize)
        params["rn"] = "" + pageSize
        buildRetrofit().create(ILoadImage::class.java).loadImageList(params)
                .subscribeOn(Schedulers.io())
                .timeout(12, TimeUnit.SECONDS)
                .unsubscribeOn(Schedulers.io())
                .map(ImageLoadCallback())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ imageDataResult ->
                    view?.refreshImageData(imageDataResult)
                }, { e ->
                    Log.e("LoadImagePresenter", e.message, e.cause)
                    view.loadFail()
                })

    }

    private fun buildRetrofit(): Retrofit {
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
                .baseUrl(AppConstant.BAIDU_IMAGE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

}