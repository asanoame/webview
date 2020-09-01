package com.xiaoyu.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewStub
import androidx.appcompat.widget.Toolbar
import com.xiaoyu.webview.utils.AutoServiceLoader
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
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initWebFragment() {
        val url = intent.getStringExtra(WEB_URL) ?: ""
        LogUtils.d(msg = "url = $url")
        mWebFragment = AutoServiceLoader.load(IWebViewService::class.java).getWebFragment(url)
        supportFragmentManager.beginTransaction()
            .add(R.id.web_fragment, mWebFragment)
            .commit()
    }
}