package com.xiaoyu.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.xiaoyu.webview.WebViewKit.errorCallback
import com.xiaoyu.webview.WebViewKit.loadingCallback
import com.xiaoyu.webview.loadsir.ErrorCallback
import com.xiaoyu.webview.loadsir.LoadingCallback
import com.xiaoyu.webview.utils.Contacts.WEB_URL
import com.xiaoyu.webview.utils.LogUtils


class WebFragment : Fragment() {

    private lateinit var mWebView: WebView
    private lateinit var mLoadService: LoadService<Any>

    private var isSuccess = true

    private val mWebViewClient = object : WebViewClient() {
        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError
        ) {
            LogUtils.d(msg = "加载失败")
            isSuccess = false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            isSuccess = true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            LogUtils.d(msg = "加载完毕, url=$url")
            if (url == "about:blank") {
                LogUtils.d(msg = "加载空白页，返回")
                LogUtils.d(msg = "-----------------------")
                return
            }
            if (isSuccess) {
                LogUtils.d(msg = "加载成功，显示正常页面")
                mLoadService.showSuccess()
            } else {
                LogUtils.d(msg = "加载失败,显示Error界面")
                mLoadService.showCallback(errorCallback)
            }
            LogUtils.d(msg = "-----------------------")
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initLoadSir(inflater, container)
    }

    private fun initLoadSir(inflater: LayoutInflater, container: ViewGroup?): View {

        val loadSir = LoadSir.Builder()
            .addCallback(loadingCallback.newInstance())
            .addCallback(errorCallback.newInstance())
            .build()

        val view = inflater.inflate(R.layout.fragment_web_view, container, false)
        initWebView(view)
        mLoadService = loadSir.register(view) {
            mLoadService.showCallback(loadingCallback)
            mWebView.reload()
        }
        return mLoadService.loadLayout
    }

    private fun initWebView(view: View) {
        mWebView = view.findViewById(R.id.web_view)
        mWebView.settings.javaScriptEnabled = true
        mWebView.webViewClient = mWebViewClient
        mWebView.webChromeClient = mWebChromeClient
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mLoadService.showCallback(loadingCallback)
        mWebView.loadUrl(arguments?.getString(WEB_URL) ?: "")
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) = WebFragment().apply {
            arguments = Bundle().apply {
                putString(WEB_URL, url)
            }
        }
    }
}