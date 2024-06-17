package com.example.myapplication.common.util;

import com.example.myapplication.BuildConfig
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.noear.snack.ONode
import java.util.concurrent.TimeUnit


object HttpUtils {

    val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaTypeOrNull();

    val FORM_DATA = "multipart/form-data; charset=utf-8".toMediaTypeOrNull();

    private val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .build();

    private fun requestBody(data: Map<String, String>?) : FormBody {
        val builder = FormBody.Builder()
        data?.forEach{
            builder.add(it.key, it.value)
        }
        return builder.build()
    }

    fun requestBody(data: Any) : RequestBody {
       return ONode.serialize(data).toRequestBody(MEDIA_TYPE_JSON)
    }

    private fun url(url: String?, params: Map<String, Any>?): String {
        val param = StringBuilder()
        if (params != null) {
            if (params.isNotEmpty()){
                param.append("?")
            }
            params.forEach{
                param.append(it.key).append("=").append(it.value.toString()).append("&")
            }
        }
        if (url != null) {
            if (url.startsWith("http")){
                return "$url$param"
            }
        }
        return "${BuildConfig.BASE_URL}$url$param"
    }

    private fun headers(header :Map<String, String>?) : Headers{
        val builder =  Headers.Builder()
        header?.forEach{
            builder.add(it.key, it.value)
        }
        return builder.build()
    }

    fun get(url: String, params: HashMap<String, Any> = hashMapOf()): Response? {
        val request: Request = Request.Builder()
            .get()
            .url(url(url, params))
            .build();
        return execute(request);
    }

    fun post(url: String, body: Any, params: Map<String, String> = hashMapOf()): Response? {
        val request: Request = Request.Builder()
            .url(url(url, params))
            .post(requestBody(body))
            .build()
        return execute(request);
    }

    fun post(dto: HttpReqDto): Response? {
        val request: Request = Request.Builder()
            .url(url(dto.url, dto.params))
            .post(requestBody(dto.body))
            .headers(headers(dto.headers))
            .build()
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
