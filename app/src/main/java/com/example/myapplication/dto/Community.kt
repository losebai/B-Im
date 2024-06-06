package com.example.myapplication.dto

import com.example.myapplication.entity.UserEntity

data class CommunityEntity(val userEntity: UserEntity,
                           val title: String,
                           val message: String,
                           val images: List<String>,
                           val createTime: String)