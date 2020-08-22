package com.alexkudin.chackjokes.documentation

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.alexkudin.chackjokes.R
import com.alexkudin.chackjokes.helpers.Constants.DOCUMENTATION_URL
import com.alexkudin.chackjokes.helpers.Constants.USER_AGENT
import kotlinx.android.synthetic.main.fragment_web.*

class WebFragment: Fragment(R.layout.fragment_web) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWebView()
        if (savedInstanceState == null)
            webView.loadUrl(DOCUMENTATION_URL)
        else
            webView.restoreState(savedInstanceState)
        retainInstance = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }



    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        webView.apply {
            webViewClient = JokesWebViewClient()
            webChromeClient = WebChromeClient()
            settings.apply {
                javaScriptEnabled = true
                useWideViewPort = true
                userAgentString = USER_AGENT
            }
        }
    }


    private inner class JokesWebViewClient: WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return false
        }

        @TargetApi(Build.VERSION_CODES.M)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBarWebView?.visibility = VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBarWebView?.visibility = GONE
        }
    }
}