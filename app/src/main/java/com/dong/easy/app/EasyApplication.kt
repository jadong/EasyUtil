package com.dong.easy.app

import android.app.Application
import android.content.Context

/**
 *
 * Created by zengwendong on 2017/8/22.
 */
class EasyApplication : Application() {

    companion object{
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this

        //全局异常捕获
        CrashHandler.Companion.instance.init(this)
    }

}