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

        if (url!!.contains("empire")) {

            val parameters = try {
                UrlToken.getData(url)
            } catch (e: Exception) {
                null
            }

            if (parameters == null) {
                // reload imgur page until user logs in successfully
//                val authWebView = activity.findViewById<WebView>(R.id.authWebView)
//                val authUrl = activity.OAUTH_LINK

//                authWebView.loadUrl(authUrl)
                activity.webview.loadUrl((url))
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