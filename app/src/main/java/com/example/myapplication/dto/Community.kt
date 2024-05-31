package com.example.myapplication.dto

import com.example.myapplication.entity.UserEntity

data class CommunityEntity(val userEntity: UserEntity,
                           val message: String,
                           val images: List<String>,
                           val createTime: String)