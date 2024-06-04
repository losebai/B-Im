package com.example.myapplication.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.remote.entity.AppUserEntity


@Entity(tableName = "app_users")
data class UserEntity (
    @PrimaryKey
    var id: Long = 0L,
    var name: String = "",
    var imageUrl: String = "",
    var note: String = "",
    var createTime: String = "",
)

fun UserEntity.toAppUserEntity(deviceNumber: String) = AppUserEntity(id, name, imageUrl, note, deviceNumber)