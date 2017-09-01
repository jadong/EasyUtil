package com.dong.easy.main

import android.content.Intent
import com.dong.easy.R
import com.dong.easy.base.BaseActivity
import com.dong.easy.scan.ScanActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/8/22.
 */
class MainActivity : BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        btn_scan.setOnClickListener {
            _ ->

            startActivity(Intent(MainActivity@this,ScanActivity::class.java))

        }
    }

}