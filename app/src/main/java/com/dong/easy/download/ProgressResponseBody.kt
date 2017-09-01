package com.dong.easy.download

import java.io.IOException

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Okio
import okio.Source

/**
 * Created by zengwendong on 2017/6/20.
 */
class ProgressResponseBody(private val responseBody: ResponseBody, private val listener: ProgressListener) : ResponseBody() {
    private var bufferedSource: BufferedSource? = null
    override fun contentType(): MediaType {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource {
        if (null == bufferedSource) {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource!!
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            internal var totalBytesRead = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                listener.onProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1L)
                return bytesRead
            }
        }
    }
}
