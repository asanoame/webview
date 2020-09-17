package com.xiaoyu.webview.webviewprocess

/**
 * XiaoYu
 * 2020/9/12 22:35
 * WebView 改变回调
 */
interface WebViewCallback {
    /**
     * 当页面开始加载的时候
     * [url] 加载的url
     */
    fun onLoadStart(url: String)

    /**
     * 当页面加载结束的时候
     * [url] 加载的url
     */
    fun onLoadFinish(url: String)

    /**
     * 页面加载失败
     * 在[onLoadFinish]后面回调
     */
    fun onError()

    /**
     * 页面加载成功
     * 在[onLoadFinish]后面回调
     */
    fun onSuccess()

    /**
     * 当标题更改的时候
     * [title] 更新的标题
     */
    fun onUpdateTitle(title: String)
}