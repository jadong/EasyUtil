package com.dong.easy.download

/**
 * Created by zengwendong on 2017/6/20.
 */
interface ProgressListener {
    /**
     * @param progress     已经下载或上传字节数
     * @param total        总字节数
     * @param done        完成
     */
    fun onProgress(progress: Long, total: Long, done: Boolean)

}
