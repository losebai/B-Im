package com.example.myapplication.ui

import android.content.Intent
import android.util.Log
import android.webkit.URLUtil
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.fragment.project.ui.web.WebViewNavigator
import com.example.myapplication.common.ui.web.WebViewManager


@Composable
fun CustomWebView(
    originalUrl: String,
    navigator: WebViewNavigator,
    modifier: Modifier = Modifier,
    goBack: () -> Unit = {},
    goForward: () -> Unit = {},
    shouldOverrideUrl: (url: String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
) {
    val url by remember { mutableStateOf(originalUrl) }
    var webView by remember { mutableStateOf<WebView?>(null) }
    BackHandler(true) {
        navigator.navigateBack()
    }
    webView?.let {
        LaunchedEffect(it, navigator) {
            with(navigator) {
                handleNavigationEvents(
                    onBack = {
                        if (WebViewManager.back(it)) {
                            goBack()
                        } else {
                            onNavigateUp()
                        }
                    },
                    onForward = {
                        if (WebViewManager.forward(it)) {
                            goForward()
                        }
                    },
                    reload = {
                        it.reload()
                    }
                )
            }
        }
    }
    AndroidView(
        factory = { context ->
            WebViewManager.obtain(context, url).apply {
                webViewClient = object : WebViewClient() {

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        if (view == null || request == null) {
                            return false
                        }
                        val requestUrl = request.url.toString()
                        if (!URLUtil.isValidUrl(requestUrl)) {
                            try {
                                view.context.startActivity(Intent(Intent.ACTION_VIEW, request.url))
                            } catch (e: Exception) {
                                Log.e(this.javaClass.name, e.message.toString())
                                onNavigateUp()
                            }
                            return true
                        }
                        // request.isRedirect == true为重定向，则交给WebView处理，解决重定向白屏的关键
                        if (!request.isRedirect && URLUtil.isNetworkUrl(requestUrl) && requestUrl != url) {
                            shouldOverrideUrl(requestUrl)
                            return true
                        }
                        return false
                    }

                }
                this.loadUrl(url)
            }.also { webView = it }
        },
        modifier = modifier,
        onRelease = {
            WebViewManager.recycle(it)
        }
    )
}
