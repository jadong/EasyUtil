package com.dong.easy.image.setting

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/11/30.
 */
class SpacesItemDecoration : RecyclerView.ItemDecoration {

    private var space: Int = 0

    constructor(space: Int) {
        this.space = space
    }

    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView, state: RecyclerView.State?) {
        outRect.left = space
        outRect.right = space
        //outRect.top = space
    }

}