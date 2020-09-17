package com.xiaoyu.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewStub
import androidx.appcompat.widget.Toolbar
import com.xiaoyu.webview.utils.AutoServiceLoader
import com.xiaoyu.webview.utils.Contacts.WEB_IS_CAN_REFRESH
import com.xiaoyu.webview.utils.Contacts.WEB_IS_SHOW_TOOLBAR
import com.xiaoyu.webview.utils.Contacts.WEB_TITLE
import com.xiaoyu.webview.utils.Contacts.WEB_URL
import com.xiaoyu.webview.utils.LogUtils

class WebActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private lateinit var mWebFragment: WebFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        initToolbar()
        initWebFragment()
    }

    private fun initToolbar() {
        if (!intent.getBooleanExtra(WEB_IS_SHOW_TOOLBAR, true)) {
            return
        }
        val toolbarStub = findViewById<ViewStub>(R.id.toolbar_stub).inflate()
        toolbar = toolbarStub.findViewById(R.id.toolbar)
        toolbar.title = intent.getStringExtra(WEB_TITLE)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initWebFragment() {
        val url = intent.getStringExtra(WEB_URL) ?: ""
        val isCanRefresh = intent.getBooleanExtra(WEB_IS_CAN_REFRESH, true)
        LogUtils.d(msg = "url = $url,isCanRefresh=$isCanRefresh")
        mWebFragment =
            AutoServiceLoader.load(IWebViewService::class.java).getWebFragment(url, isCanRefresh)
        supportFragmentManager.beginTransaction()
            .add(R.id.web_fragment, mWebFragment)
            .commit()
    }

    fun updateTitle(title: String) {
        if (this::toolbar.isInitialized) {
            toolbar.title = title
        }
    }

    override fun onBackPressed() {
        LogUtils.d(msg = "Activity onBackPressed 触发")
        val isConsume = mWebFragment.dispatchBackClick()
        if (isConsume) {
            LogUtils.d(msg = "isConsume=$isConsume,WebFragment 消费点击事件")
        } else {
            LogUtils.d(msg = "isConsume=$isConsume,WebFragment 没有消费消费点击事件")
            super.onBackPressed()
        }
    }
}