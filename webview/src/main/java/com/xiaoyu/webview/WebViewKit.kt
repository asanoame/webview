package com.xiaoyu.webview

import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.xiaoyu.webview.loadsir.ErrorCallback
import com.xiaoyu.webview.loadsir.LoadingCallback

/**
 * XiaoYu
 * 2020/9/2 00:26
 */
class WebViewKit private constructor() {
    companion object {
        private var sInstance: WebViewKit? = null
            get() {
                if (field == null) {
                    field = WebViewKit()
                }
                return field
            }

        @Synchronized
        fun getInstance(): WebViewKit = sInstance!!
    }

    var loadingCallback: Class<out LoadingCallback> = LoadingCallback::class.java
        private set
    var errorCallback: Class<out ErrorCallback> = ErrorCallback::class.java
        private set

    var refreshHeader: Class<out RefreshHeader> = ClassicsHeader::class.java

    fun setLoadingCallback(loadingCallback: Class<out LoadingCallback>): WebViewKit {
        this.loadingCallback = loadingCallback
        return this
    }

    fun setErrorCallback(errorCallback: Class<out ErrorCallback>): WebViewKit {
        this.errorCallback = errorCallback
        return this
    }

    fun setRefreshHeader(refreshHeader: Class<out RefreshHeader>): WebViewKit {
        this.refreshHeader = refreshHeader
        return this
    }
}