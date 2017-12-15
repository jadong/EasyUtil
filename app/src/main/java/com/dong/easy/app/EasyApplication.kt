package com.dong.easy.app

import android.app.Application
import android.content.Context
import com.alexvasilkov.gestures.internal.GestureDebug

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
        GestureDebug.setDebugFps(true)
        GestureDebug.setDebugAnimator(true)

        //全局异常捕获
        CrashHandler.Companion.instance.init(this)
    }

}