package com.example.myapplication.service

import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.util.HttpUtils
import com.example.myapplication.dto.UserGameDto
import com.example.myapplication.dto.UserPoolRakingDto
import okhttp3.Response
import org.noear.snack.ONode

class RakingService {

    fun getUserGameDto(userId: Long, isProd: Boolean, gameName: String) : UserGameDto {
        val params: HashMap<String, Any> = hashMapOf()
        params["userId"] = userId
        params["isProd"] = isProd
        params["gameName"] = gameName
        val res: Response? = HttpUtils.get(AppAPI.RakingAPI.GET_USER_GAME, params)
        if (res?.isSuccessful == true) {
            val str = res.body?.string()
            val json = ONode.load(str)
            return json["data"].toObject(UserGameDto::class.java)
        }
        return UserGameDto()
    }


    fun getUserPoolRakingDto(poolType: Int, isProd: Boolean, gameName: String) : List<UserPoolRakingDto> {
        val params: HashMap<String, Any> = hashMapOf()
        params["poolType"] = poolType
        params["isProd"] = isProd
        params["gameName"] = gameName
        val res: Response? = HttpUtils.get(AppAPI.RakingAPI.GET_RAKING_LIST, params)
        if (res?.isSuccessful == true) {
            val str = res.body?.string()
            val json = ONode.load(str)
            return json["data"].toObjectList(UserPoolRakingDto::class.java)
        }
        return listOf()
    }

}