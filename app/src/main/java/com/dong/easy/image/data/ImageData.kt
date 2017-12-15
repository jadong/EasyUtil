package com.dong.easy.image.data

import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/11/30.
 */
class ImageData : Serializable {

    @SerializedName("hoverURL")
    val hoverURL: String = ""
    @SerializedName("middleURL")
    val middleURL: String = ""


    @SerializedName("thumbURL")
    val thumbURL: String = ""
        get() {
            var smallImage = field
            if (TextUtils.isEmpty(smallImage)) {
                smallImage = hoverURL
                if (TextUtils.isEmpty(smallImage)) {
                    smallImage = middleURL
                }
            }

            return smallImage
        }

    @SerializedName("objURL")
    val objURL: String = "" //原图
    get() {
        var bigImage = field
        if (TextUtils.isEmpty(bigImage)) {
            bigImage = middleURL
            if (TextUtils.isEmpty(bigImage)) {
                bigImage = hoverURL
                if (TextUtils.isEmpty(bigImage)) {
                    bigImage = thumbURL

                }
            }
        }
        return bigImage
    }


    @SerializedName("fromURL")
    val fromURL: String = "" //来源站
    @SerializedName("fromPageTitleEnc")
    val fromPageTitleEnc: String = ""
    @SerializedName("type")
    val type: String = ""
    @SerializedName("height")
    val height: Int = -1
    @SerializedName("width")
    val width: Int = -1

}