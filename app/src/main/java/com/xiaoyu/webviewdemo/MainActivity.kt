package com.xiaoyu.webviewdemo

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xiaoyu.webview.WebActivity
import com.xiaoyu.webview.utils.Contacts.WEB_IS_CAN_REFRESH
import com.xiaoyu.webview.utils.Contacts.WEB_IS_SHOW_TOOLBAR
import com.xiaoyu.webview.utils.Contacts.WEB_TITLE
import com.xiaoyu.webview.utils.Contacts.WEB_URL
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        open_web.setOnClickListener {
            startActivity(Intent().apply {
                component = ComponentName(this@MainActivity, WebActivity::class.java)
                putExtra(WEB_URL, "file:///android_asset/demo.html")
                putExtra(WEB_TITLE, "本地测试")
                putExtra(WEB_IS_SHOW_TOOLBAR, true)
                putExtra(WEB_IS_CAN_REFRESH, true)
            })
        }
    }
}