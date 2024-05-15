package com.example.myapplication.service

import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.consts.AppDynamicClass
import com.example.myapplication.common.util.HttpUtils
import com.example.myapplication.entity.AppDynamic
import mu.KotlinLogging
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.noear.snack.ONode
import java.util.Collections

private val logger = KotlinLogging.logger {}
class MessageService {

    companion object {
        private val appDynamic: AppDynamic = AppDynamic()
    }


    fun getAppDynamic(id: Long): AppDynamic {
        val res: Response? = HttpUtils.get("${AppAPI.Community.GET_DYNAMIC}$id")
        if (res?.isSuccessful == true) {
            val json = ONode.load(res.body?.string())
            if (!json["code"].equals(200)){
                logger.error {  "错误: ${json["description"]}"}
            }
            return json["data"].toObject(AppDynamicClass)
        }
        return appDynamic

    }

    fun appDynamicPage(
        page: Int,
        size: Int,
        appDynamic: AppDynamic = MessageService.appDynamic
    ): List<AppDynamic> {
        val requestBody: RequestBody =
            ONode.serialize(appDynamic).toRequestBody(HttpUtils.MEDIA_TYPE_JSON)
        val res: Response? = HttpUtils.post("${AppAPI.Community.GET_DYNAMIC_PAGE}?page=$page&size=$size", requestBody)
        if (res?.isSuccessful == true) {
            val json = ONode.load(res.body?.string())
            if (!json["code"].equals(200)){
                logger.error {  "错误: ${json["description"]}"}
            }
            return json["data"]["records"].toObjectList(AppDynamicClass)
        }
        return Collections.emptyList()
    }

    fun save(appDynamic: AppDynamic): Boolean {
        val requestBody: RequestBody = ONode.serialize(appDynamic).toRequestBody(HttpUtils.MEDIA_TYPE_JSON)
        val res: Response? = HttpUtils.post(AppAPI.Community.GET_DYNAMIC_SAVE, requestBody)
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            if (!json["code"].equals(200)){
                logger.error {  "错误: ${json["description"]}"}
            }
            return json["data"].toObject(Boolean.Companion::class.java)
        }
        return false
    }
}