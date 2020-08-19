package com.xiaoyu.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewStub
import androidx.appcompat.widget.Toolbar
import com.xiaoyu.webview.utils.Contacts.WEB_IS_SHOW_TOOLBAR
import com.xiaoyu.webview.utils.Contacts.WEB_TITLE

class WebActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        initToolbar()
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
}