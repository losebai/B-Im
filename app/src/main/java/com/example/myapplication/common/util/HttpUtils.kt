package com.example.myapplication.common.util;

import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.Objects


object HttpUtils {

    val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaTypeOrNull();

    private val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .build();

    private fun url(url: String, params: HashMap<String, Any>): String {
        var param = ""
        if (params.isNotEmpty()){
            param += "?"
        }
        params.forEach{
            param += it.key + "=" + it.value.toString()
        }
        if (isDebugInspectorInfoEnabled) {
            return "http://192.168.20.119:8082$url$param"
        }
        return "http://192.168.20.119:8082$url$param"
//        return "http://121.40.62.167:8082$url"
    }

    fun get(url: String, params: HashMap<String, Any> = hashMapOf()): Response {
        val request: Request = Request.Builder()
            .get()
            .url(url(url, params))
            .build();
        return client.newCall(request).execute();
    }

    fun post(url: String, body: RequestBody, params: HashMap<String, Any> = hashMapOf()): Response {
        val request: Request = Request.Builder()
            .post(body)
            .url(url(url, params))
            .build();
        return client.newCall(request).execute();
    }

}
