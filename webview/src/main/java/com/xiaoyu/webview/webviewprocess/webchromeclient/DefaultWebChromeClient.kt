package com.xiaoyu.webview.webviewprocess.webchromeclient

import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.xiaoyu.webview.WebViewCallback
import com.xiaoyu.webview.utils.LogUtils

/**
 * XiaoYu
 * 2020/9/12 22:57
 */
class DefaultWebChromeClient(private val webViewCallback: WebViewCallback) : WebChromeClient() {
    override fun onReceivedTitle(view: WebView?, title: String) {
        if (title == "about:blank") {
            LogUtils.d(msg = "标题是空页面标题，不需要刷新")
            return
        }
        LogUtils.d(msg = "刷新标题 title=$title")
        webViewCallback.onUpdateTitle(title)
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
        LogUtils.d(msg = consoleMessage.message())
        return super.onConsoleMessage(consoleMessage)
    }
}