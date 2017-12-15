package com.dong.easy.webview;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/8/14.
 */
public class WebViewActivity extends BaseActivity {

    private WebView webView;

    @Override
    public int getContentView() {
        return R.layout.activity_web_view;
    }

    @Override
    public boolean isShowBack() {
        return true;
    }

    @Override
    public void initData() {

        webView = (WebView) findViewById(R.id.webView);

        String url = getIntent().getStringExtra("url");

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAllowFileAccess(true);  //设置可以访问文件
        webView.getSettings().setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webView.getSettings().setDatabaseEnabled(true);   //开启 database storage API 功能
        webView.getSettings().setAppCacheEnabled(true);//开启 Application Caches 功能
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.loadUrl(url);
    }
}
