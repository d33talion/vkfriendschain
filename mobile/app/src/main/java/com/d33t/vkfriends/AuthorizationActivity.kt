package com.d33t.vkfriends

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_authorization.*

class AuthorizationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        val wbWebView = findViewById<WebView>(R.id.webView)
        wbWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url!!.startsWith("https://oauth.vk.com/blank.html#")) {
                    val query = url.substring(url.indexOf('#') + 1)
                    val accessToken = query.split('&')[0].split('=')[1]
                    val expirationTime = query.split('&')[1].split('=')[1]
                    val userId = query.split('&')[2].split('=')[1]

                    val intentResult = Intent().apply {
                        putExtra("ACCESS_TOKEN", accessToken)
                        putExtra("EXPIRATION_TIME", expirationTime)
                        putExtra("USER_ID", userId)
                    }
                    setResult(Activity.RESULT_OK, intentResult)
                    finish()
                }

                view?.loadUrl(url)
                return true
            }
        }

        wbWebView.loadUrl("https://oauth.vk.com/authorize?client_id=6253520&redirect_uri=https://oauth.vk.com/blank.html&display=mobile&scope=2&response_type=token&v=5.35")
    }
}
