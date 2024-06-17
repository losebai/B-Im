package com.example.myapplication.dto

import org.noear.snack.annotation.ONodeAttr

enum class LotteryPollEnum(@ONodeAttr val value: Int) {
    INIT_ROLE(0),
    INIT_WEAPON(1),
    UP_ROLE(5),
    UP_WEAPON(6),
    C_ROLE(7),
    C_WEAPON(8);
}

data class LotteryCount(
    val roleId: Long,
    val roleImageUri: String,
    val count: Int,
    val poolId: Int,
    val isOk: Boolean
)

data class LotteryPool(
    val group: String,
    val poolId: Int,
    val poolName: String,
    val poolBg: String,
    val poolImageUri: String,
    val poolType: LotteryPollEnum,
    val array: List<String>,
)