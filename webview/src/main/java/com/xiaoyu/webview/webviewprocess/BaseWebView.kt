package com.xiaoyu.webview.webviewprocess

import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.xiaoyu.webview.WebViewComponent
import com.xiaoyu.webview.utils.LogUtils
import com.xiaoyu.webview.webviewprocess.settings.WebViewDefaultSettings
import com.xiaoyu.webview.webviewprocess.webchromeclient.DefaultWebChromeClient
import com.xiaoyu.webview.webviewprocess.webviewclient.DefaultWebViewClient

/**
 * XiaoYu
 * 2020/9/12 23:20
 */
class BaseWebView : WebView {
    constructor(context: Context)
            : super(context)

    constructor(context: Context, attrs: AttributeSet)
            : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    init {
        WebViewDefaultSettings.newInstance().setSettings(this)
        addJavascriptInterface(this, WebViewComponent.getInstance().javascriptInterfaceName)
        CommandDispatcher.getInstance().initAidlConnection()
    }

    fun registerWebViewCallback(viewViewCallback: WebViewCallback) {
        webChromeClient = DefaultWebChromeClient(viewViewCallback)
        webViewClient = DefaultWebViewClient(viewViewCallback)
    }

    @JavascriptInterface
    fun takeNativeAction(jsParams: String) {
        LogUtils.i(msg = "jsParams=$jsParams")
        val gson = WebViewComponent.getInstance().getGson()
        val entity = gson.fromJson(jsParams, JSParamEntity::class.java) ?: return
        val params = WebViewComponent.getInstance().getGson().toJson(entity.param)
        CommandDispatcher.getInstance().executeCommand(entity.name, params)
    }
}