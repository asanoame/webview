package com.xiaoyu.webviewdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xiaoyu.webview.WebViewComponent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        open_web.setOnClickListener {
            WebViewComponent.getInstance().startWeb(
                this, "file:///android_asset/demo.html", "本地测试",
                isShowToolbar = true,
                isCanRefresh = false
            )
        }
    }
}