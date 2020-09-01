package com.xiaoyu.webview

import android.content.Context
import com.xiaoyu.webview.loadsir.ErrorCallback
import com.xiaoyu.webview.loadsir.LoadingCallback

/**
 * XiaoYu
 * 2020/8/19 23:58
 * webview 组件打开的接口
 * 如果要实现组件化的话请把此接口下沉
 */
interface IWebViewService {
    fun startWebActivity(context: Context, url: String, title: String, isShowToolbar: Boolean)
    fun getWebFragment(url: String): WebFragment
}