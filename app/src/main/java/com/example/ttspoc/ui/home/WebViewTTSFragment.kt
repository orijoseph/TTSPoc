package com.example.ttspoc.ui.home

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import com.example.ttspoc.R
import kotlinx.android.synthetic.main.fragment_home.*

class WebViewTTSFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webview.apply {
            settings.apply {
                setAppCachePath(context.cacheDir.path)
                setAppCacheEnabled(true)
                cacheMode = WebSettings.LOAD_DEFAULT
                javaScriptEnabled = true
                allowFileAccess = true
                allowFileAccessFromFileURLs = true
                allowUniversalAccessFromFileURLs = true
                allowContentAccess = true
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
            }
            loadUrl("https://cv6oy.csb.app/")
        }
    }
}