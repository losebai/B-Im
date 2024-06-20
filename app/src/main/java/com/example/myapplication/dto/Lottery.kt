package com.example.myapplication.dto

import com.example.myapplication.mc.dto.MCAwardCart
import org.noear.snack.annotation.ONodeAttr

enum class LotteryPollEnum(@ONodeAttr val value: Int) {
    INIT_ROLE(1),
    INIT_WEAPON(2),
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
    var poolId: Int = 0,
    var poolName: String = "",
    var poolBg: String = "",
    var poolImageUri: String = "",
    var poolType: LotteryPollEnum = LotteryPollEnum.INIT_WEAPON,
    var group: String = "",
    var array: ArrayList<String> = ArrayList(),
    var maxCount: Int = 80,
    var probability4: Double = 0.0,
    var probability5: Double = 0.0,
    var startTime: String = "",
    var endTime: String = "",
    var awardCart: MCAwardCart = MCAwardCart()
)


data class Award(
    var id : Int = 0,
    var name: String = "",
    var star: Int = 0,
    var imageUri : String = "",
)