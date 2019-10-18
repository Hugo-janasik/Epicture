package com.example.epicture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_main.*
import android.R.string.no
import android.R.attr.name
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Menu
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

import com.example.epicture.home
import com.example.epicture.WebViewClientAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val queue = Volley.newRequestQueue(this)

        val url = "https://api.imgur.com/oauth2/authorize?client_id=f424c0c044998c8&response_type=token"


        webview.settings.javaScriptEnabled = true
        webview.webViewClient = WebViewClientAuth(this)
        webview.loadUrl((url))


//        if (webview.url == "https://www.getpostman.com/oauth2/callback")
//            Intent(this, home::class.java)
//        webview.webViewClient = WebView
        // Request a string response from the provided URL.
/*        val jsonObjectRequest = StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
                Test.text = "Response is: ${response}"
            },
            Response.ErrorListener { error -> Test.text = error.message })
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)*/
    }

}
