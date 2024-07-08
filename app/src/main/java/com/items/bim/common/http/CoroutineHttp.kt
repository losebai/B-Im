package com.items.bim.common.http

import android.content.ContextWrapper
import com.example.miaow.base.http.HttpRequest
import com.example.miaow.base.http.HttpResponse
import kotlinx.coroutines.CoroutineScope
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.noear.snack.ONode
import retrofit2.Retrofit
import retrofit2.http.*
import java.io.File

/**
 * get请求
 * @param init  http请求体
 */
suspend inline fun <reified T : HttpResponse> CoroutineScope.get(
    noinline init: HttpRequest.() -> Unit
): T {
    return CoroutineHttp.getInstance().get(init, T::class.java)
}

suspend inline fun CoroutineScope.string(
    noinline init: HttpRequest.() -> Unit
): String {
    return CoroutineHttp.getInstance().string(init)
}

/**
 * post请求
 * @param init  http请求体
 */
suspend inline fun <reified T : HttpResponse> CoroutineScope.post(
    noinline init: HttpRequest.() -> Unit
): T {
    return CoroutineHttp.getInstance().post(init, T::class.java)
}

/**
 * form请求
 * @param init  http请求体
 */
suspend inline fun <reified T : HttpResponse> CoroutineScope.form(
    noinline init: HttpRequest.() -> Unit
): T {
    return CoroutineHttp.getInstance().form(init, T::class.java)
}

/**
 * download请求
 * @param savePath 保存路径
 * @param fileName 文件名称
 * @param init  http请求体
 */
suspend inline fun CoroutineScope.download(
    savePath: String,
    fileName: String,
    noinline init: HttpRequest.() -> Unit
): HttpResponse {
    return CoroutineHttp.getInstance().download(savePath, fileName, init)
}

fun ContextWrapper.setBaseUrl(baseUrl: String) {
    CoroutineHttp.getInstance().setBaseUrl(baseUrl)
}

fun ContextWrapper.setHttpClient(client: OkHttpClient) {
    CoroutineHttp.getInstance().setHttpClient(client)
}

/**
 * retrofit + coroutines 封装的Http工具类
 */
class CoroutineHttp private constructor() {

    companion object {

        @Volatile
        private var INSTANCE: CoroutineHttp? = null

        fun getInstance() = INSTANCE ?: synchronized(CoroutineHttp::class.java) {
            INSTANCE ?: CoroutineHttp().also { INSTANCE = it }
        }

    }

    private lateinit var baseUrl: String
    private lateinit var client: OkHttpClient
    private var retrofit: Retrofit? = null
    private var service: ApiService? = null
    private var converter: Converter? = null

    fun setBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    fun setHttpClient(client: OkHttpClient) {
        this.client = client
    }

    private fun getRetrofit(): Retrofit {
        return retrofit ?: Retrofit.Builder().baseUrl(baseUrl).client(client).build().also {
            retrofit = it
        }
    }

    private fun getService(): ApiService {
        return service ?: getRetrofit().create(ApiService::class.java).also { service = it }
    }

    private fun getConverter(): Converter {
        return converter ?: ConverterImpl().also { converter = it }
    }

    suspend fun <T : HttpResponse> get(
        init: HttpRequest.() -> Unit,
        type: Class<T>,
    ): T {
        return try {
            val request = HttpRequest()
            request.init()
            val responseBody = getService().get(request.getUrl(baseUrl), request.getHeader())
            getConverter().converter(responseBody, type).apply {
                setRequestTime(request.time)
            }
        } catch (e: Exception) {
            buildResponse("-1", e.message.toString(), type)
        }
    }

    suspend fun string(
        init: HttpRequest.() -> Unit,
    ): String {
        return try {
            val request = HttpRequest()
            request.init()
            getService().get(
                request.getUrl(baseUrl),
                request.getHeader()
            ).string()
        } catch (e: Exception) {
            e.message.toString()
        }
    }

    suspend fun <T : HttpResponse> post(
        init: HttpRequest.() -> Unit,
        type: Class<T>,
    ): T {
        return try {
            val request = HttpRequest()
            request.init()
            val responseBody = getService().post(
                request.getUrl(baseUrl),
                request.getHeader(),
                request.getParam()
            )
            getConverter().converter(responseBody, type).apply {
                setRequestTime(request.time)
            }
        } catch (e: Exception) {
            buildResponse("-1", e.message.toString(), type)
        }
    }

    suspend fun <T : HttpResponse> form(
        init: HttpRequest.() -> Unit,
        type: Class<T>,
    ): T {
        return try {
            val request = HttpRequest()
            request.init()
            val responseBody = getService().form(
                request.getUrl(baseUrl),
                request.getHeader(),
                request.getMultipartBody()
            )
            getConverter().converter(responseBody, type).apply {
                setRequestTime(request.time)
            }
        } catch (e: Exception) {
            buildResponse("-1", e.message.toString(), type)
        }
    }

    suspend fun download(
        savePath: String,
        fileName: String,
        init: HttpRequest.() -> Unit
    ): HttpResponse {
        return try {
            val request = HttpRequest()
            request.init()
            getService().get(request.getUrl(), request.getHeader()).use {
                val file = File(savePath, fileName)
                it.byteStream().use { inputStream ->
                    file.writeBytes(inputStream.readBytes())
                }
            }
            buildResponse("0", "success", HttpResponse::class.java)
        } catch (e: Exception) {
            buildResponse("-1", e.message.toString(), HttpResponse::class.java)
        }
    }

    private fun <T : HttpResponse> buildResponse(code: String, msg: String, type: Class<T>): T {
        val json = """
            {
                "errorCode": "$code",
                "errorMsg": "${msg.replace("\"", "")}"
            }
        """.trimIndent()
        return getConverter().fromJson(json, type)
    }

    interface Converter {
        fun <T> converter(responseBody: ResponseBody, type: Class<T>): T

        @Throws(Exception::class)
        fun <T> fromJson(json: String, classOfT: Class<T>): T
    }

    class ConverterImpl() : Converter{

        override fun <T> converter(responseBody: ResponseBody, type: Class<T>): T {
           return  ONode.loadObj(responseBody.string()).toObject(type)
        }

        override fun <T> fromJson(json: String, classOfT: Class<T>): T {
            return  ONode.load(json).toObject(classOfT)
        }

    }

}

interface ApiService {

    @POST
    suspend fun form(
        @Url url: String = "",
        @HeaderMap header: Map<String, String>,
        @Body body: MultipartBody
    ): ResponseBody

    @FormUrlEncoded
    @POST
    suspend fun post(
        @Url url: String = "",
        @HeaderMap header: Map<String, String>,
        @FieldMap params: Map<String, String>
    ): ResponseBody

    @GET
    suspend fun get(
        @Url url: String = "",
        @HeaderMap header: Map<String, String>
    ): ResponseBody
}

