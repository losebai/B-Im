package com.example.myapplication.common;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


object HttpUtils {

    private val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .build();

    fun get(url: String): Response {
        val request: Request = Request.Builder()
            .get()
            .url(url)
            .build();
        return client.newCall(request).execute();
    }

    fun post(url: String, body: RequestBody): Response {
        val request: Request = Request.Builder()
            .post(body)
            .url(url)
            .build();
        return client.newCall(request).execute();
    }

}
