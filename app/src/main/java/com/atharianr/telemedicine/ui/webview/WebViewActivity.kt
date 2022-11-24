package com.atharianr.telemedicine.ui.webview

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.atharianr.telemedicine.databinding.ActivityWebViewBinding
import com.atharianr.telemedicine.utils.Constant

class WebViewActivity : AppCompatActivity() {

    private var _binding: ActivityWebViewBinding? = null
    private val binding get() = _binding as ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWebView(intent.getStringExtra(Constant.WEB_URL))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed() {
        binding.apply {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                super.onBackPressed()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(url: String?) {
        Log.d("cobacoba", "$url")
        if (url != null) {
            binding.apply {
                webView.webViewClient = WebViewClient()
                webView.loadUrl(url)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    webView.settings.safeBrowsingEnabled = false
                }

                val webViewSettings = webView.settings
                webViewSettings.javaScriptEnabled = true
                webViewSettings.domStorageEnabled = true
            }
        }
    }
}