package com.example.myapplication.service

import android.R.attr.data
import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.consts.UserStatus
import com.example.myapplication.common.util.HttpUtils
import com.example.myapplication.common.util.HttpUtils.MEDIA_TYPE_JSON
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.remote.entity.AppUserEntity
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.noear.snack.ONode
import org.noear.snack.core.Options
import org.noear.snack.core.utils.DateUtil
import java.util.Collections


class UserService {

    companion object {
        private val appUserEntity: AppUserEntity = AppUserEntity()
        private val options: Options = Options.def()

        init {
            options.addDecoder(UserStatus::class.java) { n, t ->
                return@addDecoder UserStatus.toUserStatus(n.toObject<Int>())
            }
            options.addEncoder(UserStatus::class.java) { n, t ->
                t.`val`().string = n.value.toString()
            }
        }
    }

     fun getUser(id: Long): AppUserEntity {
        val res: Response? = HttpUtils.get("${AppAPI.GET_USER}$id")
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            return json["data"].toObject<AppUserEntity>(AppUserEntity::class.java)
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
            return json["data"].toObject<AppUserEntity>(AppUserEntity::class.java)
        }
        return appUserEntity
    }

    fun save(user: AppUserEntity): Boolean {
        val res: Response? = HttpUtils.post(AppAPI.POST_USER_SAVE, user)
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            return json["data"].toObject(Boolean.Companion::class.java)
        }
        return false
    }

    fun getList(user: AppUserEntity): List<UserEntity> {
        val res: Response? = HttpUtils.post(AppAPI.POST_USER_LIST, user)
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            return json["data"].toObjectList(UserEntity::class.java)
        }
        return Collections.emptyList()
    }
}