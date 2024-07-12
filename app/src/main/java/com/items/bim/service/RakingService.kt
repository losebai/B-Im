package com.items.bim.service

import com.items.bim.common.consts.AppAPI
import com.items.bim.common.util.HttpUtils
import com.items.bim.dto.AppGameRole
import com.items.bim.dto.GameRoleDto
import com.items.bim.dto.UserGameDto
import com.items.bim.dto.UserPoolRakingDto
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

    fun  getAppGameRole(gameName: String) : AppGameRole {
        val params: HashMap<String, Any> = hashMapOf()
        params["gameName"] = gameName
        val res: Response? = HttpUtils.get(AppAPI.AppGameRoleRaking.GET_APP_GAME_ROLE, params)
        if (res?.isSuccessful == true) {
            val str = res.body?.string()
            val json = ONode.load(str)
            val data = json["data"]
            val updateTIme = data.get("updateTIme").toObject<String>()
            val appGameRoleRaking = data.get("appGameRoleRaking").toObject<HashMap<String, ArrayList<GameRoleDto>>>(object : HashMap<String, ArrayList<GameRoleDto>>() {}.javaClass)
            return AppGameRole(updateTIme, appGameRoleRaking)
        }
        return AppGameRole(null, mapOf())
    }

}