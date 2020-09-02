package com.xiaoyu.webview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.xiaoyu.webview.utils.Contacts.WEB_IS_CAN_REFRESH
import com.xiaoyu.webview.utils.Contacts.WEB_URL
import com.xiaoyu.webview.utils.LogUtils


class WebFragment : Fragment(), OnRefreshListener {

    companion object {
        @JvmStatic
        fun newInstance(url: String, isCanRefresh: Boolean) = WebFragment().apply {
            arguments = Bundle().apply {
                putString(WEB_URL, url)
                putBoolean(WEB_IS_CAN_REFRESH, isCanRefresh)
            }
        }
    }

    private lateinit var mWebView: WebView
    private lateinit var mRefreshLayout: SmartRefreshLayout

    private lateinit var mLoadService: LoadService<Any>

    private lateinit var mWebViewKit: WebViewKit

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
                if (mLoadService.currentCallback !is SuccessCallback) {
                    mLoadService.showSuccess()
                }
                if (mRefreshLayout.isRefreshing) {
                    mRefreshLayout.finishRefresh(true)
                }
            } else {
                LogUtils.d(msg = "加载失败,显示Error界面")
                if (mLoadService.currentCallback !is SuccessCallback) {
                    mLoadService.showCallback(mWebViewKit.errorCallback)
                }
                if (mRefreshLayout.isRefreshing) {
                    mRefreshLayout.finishRefresh(false)
                }
            }
            isSuccess = true
            LogUtils.d(msg = "-----------------------")
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String) {
            if (title == "about:blank") {
                LogUtils.d(msg = "标题是空页面标题，不需要刷新")
                return
            }
            val requireActivity = requireActivity()
            if (requireActivity is WebActivity) {
                requireActivity.updateTitle(title)
                LogUtils.d(msg = "刷新标题 title=$title")
            } else {
                LogUtils.d(msg = "activity 不是WebActivity，不需要刷新")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mWebViewKit = WebViewKit.getInstance()
        return initLoadSir(inflater, container)
    }

    private fun initLoadSir(inflater: LayoutInflater, container: ViewGroup?): View {
        val loadSir = LoadSir.Builder()
            .addCallback(mWebViewKit.loadingCallback.newInstance())
            .addCallback(mWebViewKit.errorCallback.newInstance())
            .build()

        val view = inflater.inflate(R.layout.fragment_web_view, container, false)
        initWebView(view)
        initRefreshLayout(view)
        mLoadService = loadSir.register(view) {
            mLoadService.showCallback(mWebViewKit.loadingCallback)
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

    private fun initRefreshLayout(view: View) {
        mRefreshLayout = view.findViewById(R.id.refresh_layout)
        mRefreshLayout.setEnableRefresh(arguments?.getBoolean(WEB_IS_CAN_REFRESH) ?: true)
        val constructor = mWebViewKit.refreshHeader.getConstructor(Context::class.java)
        mRefreshLayout.setRefreshHeader(constructor.newInstance(requireContext()))
        mRefreshLayout.setOnRefreshListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mLoadService.showCallback(mWebViewKit.loadingCallback)
        mWebView.loadUrl(arguments?.getString(WEB_URL) ?: "")
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mWebView.reload()
    }

    fun dispatchNavigationClick(): Boolean {
        LogUtils.d(msg = "↓----------------------↓")
        LogUtils.d(msg = "接收到Activity的返回事件分发")
        val isConsume = if (mWebView.canGoBack()) {
            mWebView.goBack()
            LogUtils.d(msg = "WebView 可以返回，消费掉本次点击")
            true
        } else {
            LogUtils.d(msg = "WebView 没有返回，不消费")
            false
        }
        LogUtils.d(msg = "↑----------------------↑")
        return isConsume
    }
}