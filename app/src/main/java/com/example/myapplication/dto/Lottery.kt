package com.example.myapplication.dto

data class LotteryCount(
    val roleId: Long,
    val roleImageUri: String,
    val count: Int,
    val poolId: Int,
    val isOk: Boolean
    )
