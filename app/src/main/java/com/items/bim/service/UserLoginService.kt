package com.items.bim.service

import com.items.bim.common.consts.AppAPI
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.util.HttpUtils
import com.items.bim.common.util.Utils
import com.items.bim.dto.UserLoginDto
import kotlinx.coroutines.MainScope
import okhttp3.Response
import org.noear.snack.ONode

class UserLoginService {

    fun sendCode(originMail: String): Boolean {
        val res: Response? =
            HttpUtils.get(AppAPI.LoginAPI.GET_SEND_CODE, hashMapOf("originMail" to originMail))
        if (res?.isSuccessful == true) {
            val json = ONode.load(res.body?.string())
            res.body?.close()
            return json.get("data").toObject<Boolean>()
        }
        return res?.isSuccessful == true
    }

    fun login(userLogin: UserLoginDto, onResponse: (String) -> Unit, onError: () -> Unit = {}) {
        HttpUtils.postAsync(AppAPI.LoginAPI.POST_USER_LOGIN, userLogin, onResponse = { res ->
            onResponse(res.toObject<String>())
        }, onError = onError)
    }

    fun register(userLogin: UserLoginDto): Boolean {
        val res: Response? = HttpUtils.post(AppAPI.LoginAPI.POST_USER_REGISTER, userLogin)
        if (res?.isSuccessful == true) {
            val json = ONode.load(res.body?.string())
            if (json.get("code").toObject<Int>().equals(200)) {
                return json.get("data").toObject<Boolean>()
            }
            Utils.message(
                MainScope(),
                json.get("description").toObject<String>(),
                SystemApp.snackBarHostState
            )
            return false
        }
        return false
    }

    fun logout(): Boolean {
        val res: Response? = HttpUtils.get(AppAPI.LoginAPI.POST_USER_LOGOUT)
        if (res?.isSuccessful == true) {
            val json = ONode.load(res.body?.string())
            return json.get("data").toObject<Boolean>()
        }
        return false
    }

}