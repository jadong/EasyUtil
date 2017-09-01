package com.dong.easy.constant

import android.os.Environment

/**
 *
 * Created by zengwendong on 2017/6/19.
 */
object AppConstant {

    val SERVER_BASE_URL = "http://172.19.32.73:9001/" //服务端host
    val SDCARD_DIR: String = Environment.getExternalStorageDirectory().path
    val CRASH_LOG_PATH = "$SDCARD_DIR/EasyApp/CrashLog/"
    val DOWNLOAD_PATH = "$SDCARD_DIR/EasyApp/Download/" //文件下载目录

}