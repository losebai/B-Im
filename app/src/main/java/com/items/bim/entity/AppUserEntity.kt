package com.items.bim.entity

import com.items.bim.common.consts.UserStatus
import com.items.bim.entity.UserEntity

data class AppUserEntity(
    var id: Long = 0L,
    var name: String = "",
    var imageUrl: String = "",
    var note: String = "",
    var deviceNumber: String? = null,
    var status: UserStatus = UserStatus.INIT
)

fun com.items.bim.entity.AppUserEntity.toUserEntity() = UserEntity(id, name, imageUrl, note, status=status)

