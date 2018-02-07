package com.dong.easy.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.dong.easy.app.EasyApplication
import java.util.*


object UIUtils {

    private var STATUS_HEIGHT = 0

    /**
     * 状态栏高度
     */
    fun getStatusHeight(): Int {
        if (STATUS_HEIGHT <= 0) {
            try {
                val resourceId = EasyApplication.appContext!!.resources
                        .getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    STATUS_HEIGHT = EasyApplication.appContext!!.resources
                            .getDimensionPixelSize(resourceId)
                }
            } catch (ex: Exception) {
            }

        }
        return STATUS_HEIGHT
    }

    fun showShortToast(message: String) {
        Toast.makeText(EasyApplication.appContext, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(message: String) {
        Toast.makeText(EasyApplication.appContext, message, Toast.LENGTH_LONG).show()
    }

    fun dip2px(dpValue: Float): Int {
        val density = EasyApplication.appContext!!.resources.displayMetrics.density
        return (dpValue * density + 0.5).toInt()
    }

    fun getScreenWidth(): Int {
        return EasyApplication.appContext!!.resources.displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return EasyApplication.appContext!!.resources.displayMetrics.heightPixels
    }

    fun hideSoftInput(view: View) {
        if (view == null) {
            return
        }
        val imm = EasyApplication.appContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm !== null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0) //强制隐藏键盘
        }

    }

    fun randomColor(): String {
        val random = Random()
        var r = Integer.toHexString(random.nextInt(256)).toUpperCase()
        var g = Integer.toHexString(random.nextInt(256)).toUpperCase()
        var b = Integer.toHexString(random.nextInt(256)).toUpperCase()

        r = if (r.length === 1) "0" + r else r
        g = if (g.length === 1) "0" + g else g
        b = if (b.length === 1) "0" + b else b

        return "#" + r + g + b
    }


}