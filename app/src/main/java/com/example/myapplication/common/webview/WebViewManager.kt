package com.example.myapplication.common.webview

import android.content.Context
import android.content.MutableContextWrapper
import android.util.Log
import android.webkit.WebView
import androidx.core.view.get
import androidx.core.view.size
import java.lang.ref.WeakReference

object WebViewManager  {


    private val webViewMap = mutableMapOf<String, WebView>()
    private val webViewQueue: ArrayDeque<WebView> = ArrayDeque()
    private val backStack: ArrayDeque<String> = ArrayDeque()
    private val forwardStack: ArrayDeque<String> = ArrayDeque()
    private var lastBackWebView: WeakReference<WebView?> = WeakReference(null)

    fun obtain(context: Context, url: String): WebView {
        val webView = if (webViewQueue.isEmpty()) {
            backStack.remove(url)
            webViewMap.getOrPut(url) {
                return WebView(context)
            }
        } else {
            webViewQueue.removeFirst()
        }
        val contextWrapper = webView as MutableContextWrapper
        contextWrapper.baseContext = context
        return webView
    }

     fun back(webView: WebView): Boolean {
        return try {
            val backLastUrl = backStack.removeLast()
            webViewMap[backLastUrl]?.let {
                webViewQueue.add(0, it)
            }
            val originalUrl = webView.originalUrl.toString()
            forwardStack.add(originalUrl)
            true
        } catch (e: Exception) {
            lastBackWebView = WeakReference(webView)
            false
        }
    }

     fun forward(webView: WebView): Boolean {
        return try {
            val forwardLastUrl = forwardStack.removeLast()
            webViewMap[forwardLastUrl]?.let {
                webViewQueue.add(0, it)
            }
            val originalUrl = webView.originalUrl.toString()
            backStack.add(originalUrl)
            true
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message.toString())
            false
        }
    }

    fun recycle(webView: WebView) {
        try {
            webView.removeView(webView[webView.size - 1])
            val originalUrl = webView.originalUrl.toString()
            if (lastBackWebView.get() != webView) {
                if (!backStack.contains(originalUrl) && !forwardStack.contains(originalUrl)) {
                    backStack.add(originalUrl)
                }
            } else {
                backStack.clear()
                forwardStack.clear()
                lastBackWebView.clear()
                webViewMap.clear()
                webViewQueue.clear()
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message.toString())
        }
    }

}
