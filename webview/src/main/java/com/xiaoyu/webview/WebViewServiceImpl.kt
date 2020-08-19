package com.xiaoyu.webview

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.google.auto.service.AutoService
import com.xiaoyu.webview.utils.Contacts.WEB_IS_SHOW_TOOLBAR
import com.xiaoyu.webview.utils.Contacts.WEB_TITLE
import com.xiaoyu.webview.utils.Contacts.WEB_URL

/**
 * XiaoYu
 * 2020/8/19 23:58
 * [com.xiaoyu.webview.IWebViewService]的实现类
 */
@AutoService(IWebViewService::class)
class WebViewServiceImpl : IWebViewService {
    override fun startWebActivity(
        context: Context,
        url: String,
        title: String,
        isShowToolbar: Boolean
    ) {
        context.startActivity(Intent().apply {
            component = ComponentName(context, WebActivity::class.java)
            putExtra(WEB_URL, url)
            putExtra(WEB_TITLE, title)
            putExtra(WEB_IS_SHOW_TOOLBAR, isShowToolbar)
        })
    }
}