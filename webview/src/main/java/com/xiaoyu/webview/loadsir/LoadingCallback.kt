package com.xiaoyu.webview.loadsir

import com.kingja.loadsir.callback.Callback
import com.xiaoyu.webview.R

/**
 * XiaoYu
 * 2020/8/23 18:42
 * Loading 界面回调
 */
open class LoadingCallback : Callback() {
    override fun onCreateView() = R.layout.loadsir_loading
}