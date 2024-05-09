package com.example.myapplication.common.util;

import com.example.myapplication.BuildConfig
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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
        return "${BuildConfig.BASE_URL}$url$param"
    }

    fun get(url: String, params: HashMap<String, Any> = hashMapOf()): Response? {
        val request: Request = Request.Builder()
            .get()
            .url(url(url, params))
            .build();
        return execute(request);
    }

    fun post(url: String, body: RequestBody, params: HashMap<String, Any> = hashMapOf()): Response? {
        val request: Request = Request.Builder()
            .post(body)
            .url(url(url, params))
            .build();
        return execute(request);
    }


    private fun execute(request: Request) : Response? {

        return try {
            client.newCall(request).execute();
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

}
