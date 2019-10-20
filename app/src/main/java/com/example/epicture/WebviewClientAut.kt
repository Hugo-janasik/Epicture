package com.example.epicture

import android.content.Intent
import android.webkit.WebView
import android.webkit.WebViewClient

import kotlinx.android.synthetic.main.activity_main.*

class WebViewClientAuth (private val activity: MainActivity) : WebViewClient() {


    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        if (url!!.contains("access_token")) {

            val parameters = UrlToken.getData(url)

            if (parameters == null) {
                activity.webview.loadUrl(("https://api.imgur.com/oauth2/authorize?client_id=f424c0c044998c8&response_type=token"))
                return
            }

            val intent = Intent(activity, Home::class.java)
            intent.putExtra("accessToken", parameters.accessToken)
            intent.putExtra("Username", parameters.Username)
            intent.putExtra("accountId", parameters.accountId)
            intent.putExtra("refreshToken", parameters.refreshToken)
            activity.startActivity(intent)
            activity.finish()
        }
    }
}