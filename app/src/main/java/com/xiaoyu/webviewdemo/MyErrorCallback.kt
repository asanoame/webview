package com.xiaoyu.webviewdemo

import com.xiaoyu.webview.loadsir.ErrorCallback

/**
 * XiaoYu
 * 2020/9/2 00:33
 */
class MyErrorCallback : ErrorCallback() {
    override fun onCreateView(): Int {
        return R.layout.my_error
    }
}