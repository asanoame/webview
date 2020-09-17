package com.xiaoyu.webview.webviewprocess

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.xiaoyu.webview.WebToMainInterface
import com.xiaoyu.webview.WebViewComponent
import com.xiaoyu.webview.mainprocess.MainCommandService
import com.xiaoyu.webview.utils.LogUtils

/**
 *webview 向主进程分发命令的分发者
 */
class CommandDispatcher private constructor() : ServiceConnection {
    companion object {
        private var sInstance: CommandDispatcher? = null
            get() {
                if (field == null) {
                    field = CommandDispatcher()
                }
                return field
            }

        @Synchronized
        fun getInstance(): CommandDispatcher {
            return sInstance!!
        }
    }

    private var webToMainInterface: WebToMainInterface? = null

    fun initAidlConnection() {
        val intent = Intent(WebViewComponent.getInstance().context, MainCommandService::class.java)
        WebViewComponent.getInstance().context.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        LogUtils.d(msg = "服务连接成功")
        webToMainInterface = WebToMainInterface.Stub.asInterface(service)
    }

    override fun onServiceDisconnected(service: ComponentName) {
        LogUtils.d(msg = "服务断开链接")
        webToMainInterface = null
        initAidlConnection()
    }

    override fun onBindingDied(name: ComponentName?) {
        LogUtils.d(msg = "服务死亡")
        webToMainInterface = null
        initAidlConnection()
    }

    fun executeCommand(name: String, params: String) {
        webToMainInterface?.handleCommand(name, params)
    }
}