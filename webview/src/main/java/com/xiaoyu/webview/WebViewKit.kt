package com.xiaoyu.webview

import com.xiaoyu.webview.loadsir.ErrorCallback
import com.xiaoyu.webview.loadsir.LoadingCallback

/**
 * XiaoYu
 * 2020/9/2 00:26
 */
object WebViewKit {
    var loadingCallback: Class<out LoadingCallback> = LoadingCallback::class.java
    var errorCallback: Class<out ErrorCallback> = ErrorCallback::class.java
}