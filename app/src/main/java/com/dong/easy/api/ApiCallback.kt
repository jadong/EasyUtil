package com.dong.easy.api

/**
 *
 * Created by zengwendong on 2017/6/17.
 */
class ApiCallback<T> : io.reactivex.functions.Function<DataResult<T>,T>{

    override fun apply(dataResult: DataResult<T>?): T {
        return dataResult?.data as T
    }
}