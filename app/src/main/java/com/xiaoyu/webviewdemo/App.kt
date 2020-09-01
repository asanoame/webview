package com.xiaoyu.webviewdemo

import android.app.Application
import com.xiaoyu.webview.WebViewKit

/**
 * XiaoYu
 * 2020/9/1 22:53
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        WebViewKit.errorCallback = MyErrorCallback::class.java
    }
}