package com.xiaoyu.webview

import android.content.Context
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.xiaoyu.webview.loadsir.ErrorCallback
import com.xiaoyu.webview.loadsir.LoadingCallback
import com.xiaoyu.webview.utils.AutoServiceLoader
import com.xiaoyu.webview.utils.LogUtils

/**
 * XiaoYu
 * 2020/9/2 00:26
 */
class WebViewComponent private constructor() {
    companion object {
        private var sInstance: WebViewComponent? = null
            get() {
                if (field == null) {
                    field = WebViewComponent()
                }
                return field
            }

        @Synchronized
        fun getInstance(): WebViewComponent = sInstance!!
    }

    var javascriptInterfaceName: String = "defaultName"
        private set

    var loadingCallback: Class<out LoadingCallback> = LoadingCallback::class.java
        private set
    var errorCallback: Class<out ErrorCallback> = ErrorCallback::class.java
        private set

    var refreshHeader: Class<out RefreshHeader> = ClassicsHeader::class.java

    fun setLoadingCallback(loadingCallback: Class<out LoadingCallback>): WebViewComponent {
        this.loadingCallback = loadingCallback
        return this
    }

    fun setErrorCallback(errorCallback: Class<out ErrorCallback>): WebViewComponent {
        this.errorCallback = errorCallback
        return this
    }

    fun setRefreshHeader(refreshHeader: Class<out RefreshHeader>): WebViewComponent {
        this.refreshHeader = refreshHeader
        return this
    }

    fun openLog(isPrintLog: Boolean): WebViewComponent {
        LogUtils.DEBUG = isPrintLog
        return this
    }

    fun setJavascriptInterfaceName(name: String): WebViewComponent {
        javascriptInterfaceName = name
        return this
    }

    fun startWeb(
        context: Context,
        url: String,
        title: String,
        isShowToolbar: Boolean,
        isCanRefresh: Boolean
    ) {
        AutoServiceLoader.load(IWebViewService::class.java)
            .startWebActivity(context, url, title, isShowToolbar, isCanRefresh)
    }

    fun getWebFragment(url: String, isCanRefresh: Boolean): WebFragment {
        return AutoServiceLoader.load(IWebViewService::class.java).getWebFragment(url, isCanRefresh)
    }
}