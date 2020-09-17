package com.xiaoyu.webview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.xiaoyu.webview.utils.Contacts.WEB_IS_CAN_REFRESH
import com.xiaoyu.webview.utils.Contacts.WEB_URL
import com.xiaoyu.webview.utils.LogUtils
import com.xiaoyu.webview.webviewprocess.BaseWebView
import com.xiaoyu.webview.webviewprocess.WebViewCallback


class WebFragment : Fragment(), OnRefreshListener, WebViewCallback {

    companion object {
        @JvmStatic
        fun newInstance(url: String, isCanRefresh: Boolean) = WebFragment().apply {
            arguments = Bundle().apply {
                putString(WEB_URL, url)
                putBoolean(WEB_IS_CAN_REFRESH, isCanRefresh)
            }
        }
    }

    private lateinit var mWebView: BaseWebView
    private lateinit var mRefreshLayout: SmartRefreshLayout

    private lateinit var mLoadService: LoadService<Any>

    private lateinit var mWebViewKit: WebViewComponent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mWebViewKit = WebViewComponent.getInstance()
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
        mWebView.registerWebViewCallback(this)
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

    /**
     * 分发来自[WebActivity]的返回事件
     */
    fun dispatchBackClick(): Boolean {
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

    override fun onLoadStart(url: String) {
        mLoadService.showCallback(mWebViewKit.loadingCallback)
    }

    override fun onLoadFinish(url: String) {}

    override fun onError() {
        mLoadService.showCallback(mWebViewKit.errorCallback)
    }

    override fun onSuccess() {
        mLoadService.showSuccess()
    }

    override fun onUpdateTitle(title: String) {
        val parentActivity = requireActivity()
        if (parentActivity is WebActivity) {
            parentActivity.updateTitle(title)
        } else {
            LogUtils.d(msg = "activity 不是WebActivity，不需要刷新")
        }
    }
}