package com.items.bim.service

import com.items.bim.common.consts.AppAPI
import com.items.bim.common.util.HttpUtils
import com.items.bim.dto.AppDynamic
import io.github.oshai.kotlinlogging.KotlinLogging
import okhttp3.Response
import org.noear.snack.ONode
import java.util.Collections

private val logger = KotlinLogging.logger {}
class MessageDynamicService {

    companion object {
        private val appDynamic: AppDynamic =
            AppDynamic()
    }


    fun getAppDynamic(id: Long): AppDynamic {
        val res: Response? = HttpUtils.get("${AppAPI.CommunityAPI.GET_DYNAMIC}$id")
        if (res?.isSuccessful == true) {
            val json = ONode.load(res.body?.string())
            if (!json["code"].equals(200)){
                logger.error {  "错误: ${json["description"]}"}
            }
            return json["data"].toObject(AppDynamic::class.java)
        }
        return appDynamic

    }

    fun appDynamicPage(
        page: Int,
        size: Int,
        appDynamic: AppDynamic = MessageDynamicService.appDynamic
    ): List<AppDynamic> {
        val res: Response? = HttpUtils.post("${AppAPI.CommunityAPI.GET_DYNAMIC_PAGE}?page=$page&size=$size", appDynamic)
        if (res?.isSuccessful == true) {
            val json = ONode.load(res.body?.string())
            if (!json["code"].equals(200)){
                logger.error {  "错误: ${json["description"]}"}
            }
            return json["data"]["records"].toObjectList(AppDynamic::class.java)
        }
        return Collections.emptyList()
    }

    fun save(appDynamic: AppDynamic): Boolean {
        val res: Response? = HttpUtils.post(AppAPI.CommunityAPI.GET_DYNAMIC_SAVE, appDynamic)
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