package com.xiaoyu.webviewdemo

import android.app.Application
import com.xiaoyu.webview.WebViewComponent

/**
 * XiaoYu
 * 2020/9/1 22:53
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        WebViewComponent.getInstance()
            .setErrorCallback(MyErrorCallback::class.java)
            .openLog(true)
            .setJavascriptInterfaceName("demoWebView")
    }
}