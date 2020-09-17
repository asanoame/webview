package com.xiaoyu.webview.mainprocess

import com.google.gson.reflect.TypeToken
import com.xiaoyu.webview.WebToMainInterface
import com.xiaoyu.webview.WebViewComponent
import com.xiaoyu.webview.command.Command
import com.xiaoyu.webview.utils.LogUtils
import java.util.*

class MainCommandsManager private constructor() : WebToMainInterface.Stub() {
    companion object {
        private var sInstance: MainCommandsManager? = null
            get() {
                if (field == null) {
                    field = MainCommandsManager()
                }
                return field
            }

        fun getInstance(): MainCommandsManager {
            return sInstance!!
        }
    }

    private val commandMap = mutableMapOf<String, Command>()

    init {
        val iterator = ServiceLoader.load(Command::class.java).iterator()
        while (iterator.hasNext()) {
            val command = iterator.next() ?: continue
            if (!commandMap.containsKey(command.name())) {
                commandMap[command.name()] = command
            }
        }
    }

    override fun handleCommand(commandName: String, params: String) {
        commandMap[commandName]?.execute(
            WebViewComponent.getInstance().getGson()
                .fromJson<MutableMap<String, Any>>(
                    params,
                    object : TypeToken<MutableMap<String, Any>>() {}.rawType
                )
        )
    }
}