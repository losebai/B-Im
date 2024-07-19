package com.items.bim.service

import com.items.bim.common.consts.AppAPI
import com.items.bim.common.consts.UserStatus
import com.items.bim.common.util.HttpUtils
import com.items.bim.entity.UserEntity
import com.items.bim.entity.AppUserEntity
import okhttp3.Response
import org.noear.snack.ONode
import org.noear.snack.core.Options
import java.util.Collections


class UserService {

    companion object {
        private val appUserEntity: com.items.bim.entity.AppUserEntity =
            com.items.bim.entity.AppUserEntity()
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

     fun getUser(id: Long): com.items.bim.entity.AppUserEntity {
        val res: Response? = HttpUtils.get("${AppAPI.GET_USER}$id")
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            return json["data"].toObject<com.items.bim.entity.AppUserEntity>(com.items.bim.entity.AppUserEntity::class.java)
        }
        return appUserEntity
    }

    fun gerUserByNumber(deviceNumber: String): com.items.bim.entity.AppUserEntity {
        val res: Response? = HttpUtils.get(AppAPI.GET_USER_BY_NUMBER, hashMapOf("deviceNumber" to deviceNumber))
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            if (!json.contains("data")){
                return appUserEntity
            }
            return json["data"].toObject<com.items.bim.entity.AppUserEntity>(com.items.bim.entity.AppUserEntity::class.java)
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
            return json["data"].toObjectList(UserEntity::class.java).sortedByDescending {
                it.status.value
            }
        }
        return Collections.emptyList()
    }
}