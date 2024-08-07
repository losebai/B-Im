package com.items.bim.dto

import com.items.bim.entity.UserEntity

data class CommunityEntity(val userEntity: UserEntity,
                           val title: String,
                           val message: String,
                           val images: List<String>,
                           val createTime: String)