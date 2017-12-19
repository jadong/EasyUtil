package com.dong.easy.image.loader;

import com.dong.easy.base.IBaseView;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/15.
 */
public interface IDownloadView extends IBaseView{

    void shareImage(String imageFilePath);

    void downloadFailure();
}
