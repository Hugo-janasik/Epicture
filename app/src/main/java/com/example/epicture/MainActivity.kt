package com.example.epicture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://api.imgur.com/oauth2/authorize?client_id=f424c0c044998c8&response_type=token"


        webview.settings.javaScriptEnabled = true
        webview.webViewClient = WebViewClientAuth(this)
        webview.loadUrl((url))
    }
}

