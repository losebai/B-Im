package com.example.myapplication.remote.entity

import com.example.myapplication.entity.UserEntity

data class AppUserEntity(
    var id: Long = 0L,
    var name: String = "",
    var imageUrl: String = "",
    var note: String = "",
    var deviceNumber: String? = null
)

fun AppUserEntity.toUserEntity() = UserEntity(id, name, imageUrl, note)

