package com.xiaoyu.webviewdemo

import android.app.Application
import android.content.Context
import com.xiaoyu.webview.WebViewComponent

/**
 * XiaoYu
 * 2020/9/1 22:53
 */
class App : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
        WebViewComponent.getInstance()
            .setErrorCallback(MyErrorCallback::class.java)
            .openLog(true)
            .setJavascriptInterfaceName("demoWebView")
    }
}