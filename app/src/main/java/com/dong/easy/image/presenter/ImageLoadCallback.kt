package com.dong.easy.image.presenter

import com.dong.easy.image.data.ImageDataResult

/**
 *
 * Created by zengwendong on 2017/6/17.
 */
class ImageLoadCallback : io.reactivex.functions.Function<ImageDataResult, ImageDataResult> {

    override fun apply(dataResult: ImageDataResult): ImageDataResult {
        return dataResult
    }

}