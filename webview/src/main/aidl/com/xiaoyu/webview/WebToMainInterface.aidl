package com.xiaoyu.webview;

//webview 进程跟主进程通信的接口
interface WebToMainInterface {
    //commandName 命令名称
    //params json字符串
    void handleCommand(String commandName,String params);
}
