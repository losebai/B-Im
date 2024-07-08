package com.items.bim.common

import com.items.bim.entity.UserEntity
import com.items.bim.entity.AppUserEntity
import com.items.bim.entity.toUserEntity

object Mapping {

    fun toUserEntityList(users: List<com.items.bim.entity.AppUserEntity>) : List<UserEntity> =
        users.map {
            it.toUserEntity()
        }
}