package com.xiaoyu.webviewdemo

import android.widget.Toast
import com.google.auto.service.AutoService
import com.xiaoyu.webview.command.Command

@AutoService(Command::class)
class ToastCommand : Command {
    override fun name(): String {
        return "showToast"
    }

    override fun execute(param: MutableMap<String, Any>) {
        Toast.makeText(App.context, param["message"].toString(), Toast.LENGTH_SHORT).show()

    }
}