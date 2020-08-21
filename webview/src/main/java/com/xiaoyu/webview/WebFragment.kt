package com.xiaoyu.webview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.xiaoyu.webview.utils.Contacts.WEB_URL


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
        initWebView(view)
    }

    private fun initWebView(view: View) {
        mWebView = view.findViewById(R.id.web_view)
        mWebView.settings.javaScriptEnabled = true
        mWebView.loadUrl(arguments?.getString(WEB_URL) ?: "")
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            WebFragment().apply {
                arguments = Bundle().apply {
                    putString(WEB_URL, url)
                }
            }
    }
}