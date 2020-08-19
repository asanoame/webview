package com.xiaoyu.webview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView


class WebFragment : Fragment() {

    private lateinit var mWebView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mWebView = view.findViewById(R.id.web_view)
        mWebView.settings.javaScriptEnabled = true
        mWebView.loadUrl("https://www.baidu.com")
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WebFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}