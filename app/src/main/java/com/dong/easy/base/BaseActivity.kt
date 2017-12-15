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

        val view = findViewById(R.id.toolbar)
        if (view != null) {
            toolbar = view as Toolbar
            if (isShowBack()) {
                toolbar!!.setNavigationOnClickListener {
                    finish()
                }
            }else{
                toolbar!!.setNavigationIcon(0)
            }
        }

        initData()

    }

    open fun isShowBack(): Boolean {
        return true
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
