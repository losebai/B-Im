package com.example.myapplication.service

import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.consts.AppUserEntityClass
import com.example.myapplication.common.util.HttpUtils
import com.example.myapplication.common.util.HttpUtils.MEDIA_TYPE_JSON
import com.example.myapplication.remote.entity.AppUserEntity
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.noear.snack.ONode
import java.util.Collections

class UserService {

    companion object{
        private val appUserEntity: AppUserEntity = AppUserEntity()
    }

     fun getUser(id: Long): AppUserEntity {
        val res: Response? = HttpUtils.get("${AppAPI.GET_USER}$id")
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            return json["data"].toObject(AppUserEntityClass)
        }
        return appUserEntity
    }

    fun gerUserByNumber(deviceNumber: String): AppUserEntity {
        val res: Response? = HttpUtils.get(AppAPI.GET_USER_BY_NUMBER, hashMapOf("deviceNumber" to deviceNumber))
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            if (!json.contains("data")){
                return appUserEntity
            }
            return json["data"].toObject(AppUserEntityClass)
        }
        return appUserEntity
    }

    fun save(user: AppUserEntity): Boolean {
        val requestBody: RequestBody = ONode.serialize(user).toRequestBody(MEDIA_TYPE_JSON)
        val res: Response? = HttpUtils.post(AppAPI.POST_USER_SAVE, requestBody)
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            return json["data"].toObject(Boolean.Companion::class.java)
        }
        return false
    }

    fun getList(user: AppUserEntity): List<AppUserEntity> {
        val requestBody: RequestBody = ONode.serialize(user).toRequestBody(MEDIA_TYPE_JSON)
        val res: Response? = HttpUtils.post(AppAPI.POST_USER_LIST, requestBody)
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            return json["data"].toObjectList(AppUserEntityClass)
        }
        return Collections.emptyList()
    }
}