package com.xiaoyu.webviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xiaoyu.webview.IWebViewService
import com.xiaoyu.webview.utils.AutoServiceLoader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        open_web.setOnClickListener {
            AutoServiceLoader.load(IWebViewService::class.java)
                .startWebActivity(this, "http://www.baidu.com", "这是一个标题", false)
        }
    }
}