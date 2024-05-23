package com.example.myapplication.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "app_users")
data class UserEntity (
    @PrimaryKey
    var id: Long = 0L,
    var name: String = "",
    var imageUrl: String = "",
    var note: String = "",
)

