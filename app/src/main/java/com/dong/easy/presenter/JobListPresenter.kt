package com.dong.easy.presenter

import com.dong.easy.api.ApiCallback
import com.dong.easy.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * Created by zengwendong on 2017/6/17.
 */
class JobListPresenter : BasePresenter<IView> {

    constructor(view: IView) : super(view)


    fun getJobList() {
        buildRetrofit().create(IJobList::class.java).getJobList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(ApiCallback())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it ->
                    //dealWithSuccess(it)
                }, {
                    //view.onLoadFailure()
                })

    }


}