package com.example.myapplication.entity

import java.time.LocalDateTime
import java.time.LocalTime



data class CommunityEntity(val userEntity: UserEntity,
                           val message: String,
                           val images: List<String>,
                           val createTime: String);