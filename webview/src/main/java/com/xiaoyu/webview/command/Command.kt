package com.xiaoyu.webview.command

/**
 * WebView 命令接口
 * [name] 命令名称
 * [execute] 接收到命令后会执行这个方法
 */
interface Command {
    fun name(): String

    /**
     *[params]由WebView传入的参数
     */
    fun execute(params: MutableMap<String, Any>)
}