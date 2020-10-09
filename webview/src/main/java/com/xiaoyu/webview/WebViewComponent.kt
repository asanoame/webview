package com.xiaoyu.webview

import android.content.Context
import com.google.gson.Gson
import com.xiaoyu.webview.loadsir.ErrorCallback
import com.xiaoyu.webview.loadsir.LoadingCallback
import com.xiaoyu.webview.utils.LogUtils

/**
 * XiaoYu
 * 2020/9/2 00:26
 */
class WebViewComponent private constructor() {
    companion object {
        private var isInit = false
        fun init(context: Context): WebViewComponent {
            sInstance!!.context = context.applicationContext
            isInit = true
            return sInstance!!
        }

        private var sInstance: WebViewComponent? = null
            get() {
                if (field == null) {
                    field = WebViewComponent()
                }
                return field
            }

        @Synchronized
        fun getInstance(): WebViewComponent {
            if (!isInit) {
                throw RuntimeException("请先调用 init(Context)")
            }
            return sInstance!!
        }
    }

    lateinit var context: Context
        private set

    var javascriptInterfaceName: String = "defaultName"
        private set

    var loadingCallback: Class<out LoadingCallback> = LoadingCallback::class.java
        private set
    var errorCallback: Class<out ErrorCallback> = ErrorCallback::class.java
        private set

    private var gson: Gson? = null

    fun setLoadingCallback(loadingCallback: Class<out LoadingCallback>): WebViewComponent {
        this.loadingCallback = loadingCallback
        return this
    }

    fun setErrorCallback(errorCallback: Class<out ErrorCallback>): WebViewComponent {
        this.errorCallback = errorCallback
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

    fun setGson(gson: Gson): WebViewComponent {
        this.gson = gson
        return this
    }

    fun getGson(): Gson {
        if (gson == null) {
            gson = Gson()
        }
        return gson!!
    }
}