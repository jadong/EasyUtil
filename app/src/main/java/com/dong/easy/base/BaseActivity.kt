package com.dong.easy.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.dong.easy.R

open abstract class BaseActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetContentView()
        setContentView(getContentView())

        toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar?.setNavigationOnClickListener {
            finish()
        }

        initData()

    }

    /**
     * 设置标题名称
     */
    fun setToolbarTitle(title: String) {
        toolbar?.title = title
    }

    open fun beforeSetContentView() {

    }

    abstract fun getContentView(): Int

    abstract fun initData()

}
