package com.items.bim.dto

import com.items.bim.entity.UserEntity

data class UserGroup(
    val groupName: String,
    val users : List<UserEntity>
)
