package com.heinhtet.deevd.webview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife

class MainActivity : AppCompatActivity() {

    @BindView(R.id.webview)
    lateinit var webView: WebView
    @BindView(R.id.progress)
    lateinit var progressBar: ProgressBar
    @BindView(R.id.progress_frame)
    lateinit var frameLayout: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        initWebView()
    }

    private fun initWebView() {
        var webSetting = webView.settings
        webSetting.javaScriptEnabled = true
        var client = WebClient()
        webView.webViewClient = client
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                frameLayout.visibility = View.VISIBLE
                progressBar.progress = newProgress
                if (newProgress == 100) {
                    frameLayout.setVisibility(View.GONE)
                }
                super.onProgressChanged(view, newProgress)
            }
        }
        webView.loadUrl("https://github.com/HeinXtet/")
        progressBar.progress = 0
//        webView.loadData("html file", "text/html; charset=utf-8", "UTF-8") using file
    }

    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: String): Boolean {
            view.loadUrl(request)
            frameLayout.visibility = View.VISIBLE
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            view.visibility = View.VISIBLE
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                return super.onKeyDown(keyCode, event)

            }
        }
        return true;
    }
}
