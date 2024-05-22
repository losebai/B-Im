package com.example.myapplication.entity

data class CommunityEntity(val userEntity: UserEntity,
                           val message: String,
                           val images: List<String>,
                           val createTime: String)