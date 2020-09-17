package com.xiaoyu.webview.webviewprocess.settings

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import com.xiaoyu.webview.BuildConfig
import com.xiaoyu.webview.utils.LogUtils

/**
 * XiaoYu
 * 2020/9/9 21:52
 */
class WebViewDefaultSettings private constructor() {
    companion object {
        fun newInstance() =
            WebViewDefaultSettings()
    }

    @Suppress("DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
    fun setSettings(webView: WebView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw()
        }
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.setSupportZoom(true)
        settings.builtInZoomControls = false
        if (isNetworkConnected(webView.context)) {
            settings.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        // 硬件加速兼容性问题有点多
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
        settings.textZoom = 100
        settings.databaseEnabled = true
        settings.setAppCacheEnabled(true)
        settings.loadsImagesAutomatically = true
        settings.setSupportMultipleWindows(false)
        settings.blockNetworkImage = false //是否阻塞加载网络图片  协议http or https
        settings.allowFileAccess = true //允许加载本地文件html  file协议
        settings.allowFileAccessFromFileURLs = false //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
        settings.allowUniversalAccessFromFileURLs =
            false //允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.savePassword = false
        settings.saveFormData = false
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.domStorageEnabled = true
        settings.setNeedInitialFocus(true)
        settings.defaultTextEncodingName = "utf-8" //设置编码格式
        settings.defaultFontSize = 16
        settings.minimumFontSize = 10 //设置 WebView 支持的最小字体大小，默认为 8
        settings.setGeolocationEnabled(true)
        settings.useWideViewPort = true
        val appCacheDir =
            webView.context.getDir("cache", Context.MODE_PRIVATE).path
        LogUtils.i(msg = "WebView cache dir: $appCacheDir")
        settings.databasePath = appCacheDir
        settings.setAppCachePath(appCacheDir)
        settings.setAppCacheMaxSize(1024 * 1024 * 80.toLong())

        // 用户可以自己设置useragent
        // mWebSettings.setUserAgentString("webprogress/build you agent info");
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
    }

    @Suppress("DEPRECATION")
    private fun isNetworkConnected(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }
}