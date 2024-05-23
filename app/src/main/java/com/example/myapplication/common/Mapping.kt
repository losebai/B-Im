package com.example.myapplication.common

import com.example.myapplication.entity.UserEntity
import com.example.myapplication.remote.entity.AppUserEntity
import com.example.myapplication.remote.entity.toUserEntity

object Mapping {

    fun toUserEntityList(users: List<AppUserEntity>) : List<UserEntity> =
        users.map {
            it.toUserEntity()
        }
}