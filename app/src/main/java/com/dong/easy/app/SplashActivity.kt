package com.dong.easy.app

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.dong.easy.R
import com.dong.easy.base.BaseActivity
import com.dong.easy.main.MainActivity

/**
 * Created by zengwendobg on 2017/8/23.
 */
class SplashActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        mHandler.sendEmptyMessageDelayed(0,1000)
    }

    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            overridePendingTransition(0, 0)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
    }

}
