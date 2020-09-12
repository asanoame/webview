package com.xiaoyu.webview.webviewprocess.webviewclient

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xiaoyu.webview.WebViewCallback
import com.xiaoyu.webview.utils.LogUtils

/**
 * XiaoYu
 * 2020/9/12 22:42
 */
class DefaultWebViewClient(private val webViewCallback: WebViewCallback) : WebViewClient() {

    private var isError = false

    override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        isError = false
        webViewCallback.onLoadStart(url)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError
    ) {
        LogUtils.d(msg = "加载失败")
        isError = true
    }

    override fun onPageFinished(view: WebView?, url: String) {
        super.onPageFinished(view, url)
        LogUtils.d(msg = "加载完毕, url=$url")
        if (url == "about:blank") {
            LogUtils.d(msg = "加载空白页，返回,不回调Callback")
            LogUtils.d(msg = "-----------------------")
            return
        }
        LogUtils.d(msg = "界面加载完毕")
        webViewCallback.onLoadFinish(url)
        if (isError) {
            LogUtils.d(msg = "加载失败，回调onError")
            webViewCallback.onError()
        } else {
            LogUtils.d(msg = "加载成功，回调onSuccess")
            webViewCallback.onSuccess()
        }
        LogUtils.d(msg = "-----------------------")
    }
}