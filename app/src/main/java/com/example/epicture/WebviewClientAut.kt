package com.example.epicture

import android.content.Context
import android.content.Intent
import android.webkit.WebView
import android.webkit.WebViewClient

import com.example.epicture.home
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class WebViewClientAuth (private val activity: MainActivity) : WebViewClient() {


    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        if (url!!.contains("access_token")) {

            val parameters = UrlToken.getData(url)

            println(parameters)
            if (parameters == null) {
                // reload imgur page until user logs in successfully
//                val authWebView = activity.findViewById<WebView>(R.id.authWebView)
//                val authUrl = activity.OAUTH_LINK

//                authWebView.loadUrl(authUrl)
                activity.webview.loadUrl(("https://api.imgur.com/oauth2/authorize?client_id=f424c0c044998c8&response_type=token"))
                return
            }

//            SecretUtils.saveSecrets(context, parameters)

            val intent = Intent(activity, home::class.java)
            intent.putExtra("url", url)
            activity.startActivity(intent)
            activity.finish()
        }
    }
}