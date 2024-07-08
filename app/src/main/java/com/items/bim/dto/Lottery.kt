package com.items.bim.dto

import com.items.bim.mc.consts.LotteryPollEnum
import com.items.bim.mc.dto.MCAwardCart


data class LotteryCount(
    val roleId: Long,
    val roleImageUri: String,
    val count: Int,
    val poolId: Int,
    val isUp: Boolean,
    val isOk: Boolean
)

data class LotteryPool(
    var poolId: Int = 0,
    var poolName: String = "",
    var poolBg: String = "",
    var poolImageUri: String = "",
    var poolType: LotteryPollEnum = LotteryPollEnum.INIT_ROLE,
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