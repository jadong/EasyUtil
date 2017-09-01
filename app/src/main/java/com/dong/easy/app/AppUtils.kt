package com.dong.easy.app

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.dong.easy.app.EasyApplication
import java.io.File

/**
 *
 * Created by zengwendong on 2017/6/19.
 */
object AppUtils {

    fun showShortToast(message: String) {
        Toast.makeText(EasyApplication.appContext, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(message: String) {
        Toast.makeText(EasyApplication.appContext, message, Toast.LENGTH_LONG).show()
    }

    /**
     * 安装apk
     */
    fun installApk(context: Context, filePath: String) {
        //apk文件的本地路径
        val apkFile = File(filePath)
        //会根据用户的数据类型打开android系统相应的Activity。
        val intent = Intent(Intent.ACTION_VIEW)
        //设置intent的数据类型是应用程序application
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive")
        //为这个新apk开启一个新的activity栈
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        //开始安装
        context.startActivity(intent)
        //关闭旧版本的应用程序的进程
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    /**
     * 打开文件
     */
    fun openFile(context: Context, file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //文件file的MIME类型
        val type = "text/plain"
        //设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), type)
        //跳转
        context.startActivity(intent)
    }
}