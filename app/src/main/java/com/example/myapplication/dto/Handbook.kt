package com.example.myapplication.dto

data class Handbook(
    val id: Long,
    val name: String,
    val type: Int,
    val desc :String,
    val imageUri: String,
    val createTime: String?
)


data class RoleBook(
    val id: Long,
    val name: String,
    val imageUri: String,
    val star: Int,
)