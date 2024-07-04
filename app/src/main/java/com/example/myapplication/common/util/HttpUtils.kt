package com.example.myapplication.common.util;

import com.example.myapplication.BuildConfig
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.noear.snack.ONode
import java.io.File
import java.util.concurrent.TimeUnit


object HttpUtils {

    private val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType();

    private val FORM_DATA = "multipart/form-data; charset=utf-8".toMediaType();

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

    private fun multipartBody(file : File?) : MultipartBody? {
        file?.let {
           return  MultipartBody.Builder()
               .setType(MultipartBody.FORM)
                //添加文件
                //RequestBody.create用于接收MediaType.parse
                //MediaType.parse("text/plain")  指定文件上传的类型
                .addFormDataPart("file", it.getName(), it.asRequestBody(FORM_DATA))
                .build();
        }
        return null
    }


    private fun requestBody(data: Any) : RequestBody {
       return ONode.serialize(data).toRequestBody(MEDIA_TYPE_JSON)
    }

    fun url(url: String?, params: Map<String, Any>?): String {
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
            .post(multipartBody(dto.file) ?: requestBody(dto.body))
            .headers(headers(dto.headers))
            .build()
        return execute(request);
    }

    fun get(dto: HttpReqDto): Response? {
        val request: Request = Request.Builder()
            .url(url(dto.url, dto.params))
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

    /**
     * 解析cookie
     *
     * @param cookie 饼干
     * @return [Map]<[String], [String]>
     */
    fun parseParams(uri: String): Map<String, String> {
        val values = uri.substring(uri.indexOf("?") + 1).split("&").dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val map: MutableMap<String, String> = LinkedHashMap(values.size)
        for (value in values) {
            val i = value.lastIndexOf("=")
            if (i > -1) {
                map[value.substring(0, i).replace(" ", "")] = value.substring(i + 1)
            }
        }
        return map
    }

    /**
     * 解析cookie
     *
     * @param cookie 饼干
     * @return [Map]<[String], [String]>
     */
    fun parseCookie(cookie: String): Map<String, String> {
        val values = cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val map: MutableMap<String, String> = LinkedHashMap(values.size)
        for (value in values) {
            val i = value.lastIndexOf("=")
            if (i > -1) {
                map[value.substring(0, i).replace(" ", "")] = value.substring(i + 1)
            }
        }
        return map
    }

}
