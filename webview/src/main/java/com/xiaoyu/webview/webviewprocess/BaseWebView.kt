package com.xiaoyu.webview.webviewprocess

import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.google.gson.reflect.TypeToken
import com.xiaoyu.webview.WebViewComponent
import com.xiaoyu.webview.command.Command
import com.xiaoyu.webview.utils.LogUtils
import com.xiaoyu.webview.webviewprocess.settings.WebViewDefaultSettings
import com.xiaoyu.webview.webviewprocess.webchromeclient.DefaultWebChromeClient
import com.xiaoyu.webview.webviewprocess.webviewclient.DefaultWebViewClient
import java.util.*

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

    private val commandMap = mutableMapOf<String, Command>()

    init {
        WebViewDefaultSettings.newInstance().setSettings(this)
        addJavascriptInterface(this, WebViewComponent.getInstance().javascriptInterfaceName)
        val iterator = ServiceLoader.load(Command::class.java).iterator()
        while (iterator.hasNext()) {
            val command = iterator.next() ?: continue
            if (!commandMap.containsKey(command.name())) {
                commandMap[command.name()] = command
            }
        }
    }

    fun registerWebViewCallback(viewViewCallback: WebViewCallback) {
        webChromeClient = DefaultWebChromeClient(viewViewCallback)
        webViewClient = DefaultWebViewClient(viewViewCallback)
    }

    @JavascriptInterface
    fun takeNativeAction(jsParams: String) {
        LogUtils.i(msg = "jsParams=$jsParams")
        val gson = WebViewComponent.getInstance().getGson()
        val entity = gson.fromJson(jsParams, JSParamEntity::class.java)
        commandMap[entity.name]?.execute(
            gson.fromJson<MutableMap<String, Any>>(
                entity.param,
                object : TypeToken<MutableMap<String, Any>>() {}.rawType
            )
        )
    }
}