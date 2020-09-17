package com.xiaoyu.webview.loadsir

import com.kingja.loadsir.callback.Callback
import com.xiaoyu.webview.R

/**
 * XiaoYu
 * 2020/8/23 18:46
 * Error 界面回调
 */
open class ErrorCallback : Callback() {
    override fun onCreateView() = R.layout.loadsir_error
}