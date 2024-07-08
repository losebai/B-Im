package com.items.bim.dto

data class LotteryAwardCountDto (
    var id: String? = null,
    var name: String = "",
    var sumCount: Int = 0,
    var star5Count: Int = 0,
    var star4Count: Int = 0,
    var avgRoleCount: Double = 0.0,
    var avgWeaponCount: Double = 0.0,
    var up: Double = 0.0,
    var avgUpCount : Double = 0.0,
    var upCount: Int = 0,
    var avgCount: Double = 0.0,
    var tag: String? = null,
    // 池子类型
    var poolLotteryAwards: List<PoolLotteryAward> = arrayListOf(),

    // 池子
    var userPoolLotteryAwards: List<UserPoolLotteryAward> = arrayListOf(),
)

data class PoolLotteryAward (
    var poolType: Int? = null,
    var poolName: String = "",
    var count: Long = 0,
    var okCount: Long = 0,
    var upCount: Long = 0,
    var avgCount: Double = 0.0,
    var avgUpCount: Double = 0.0,
    var up : Double= 0.0,
    var tag: String? = null,
    var hookAwards: List<HookAward> = ArrayList()
)

data class UserPoolLotteryAward (
    var id: String? = null,
    var imageUri: String? = null,
    var poolId: Int? = null,
    var poolName: String? = null,
    var count: Long = 0,
    var okCount: Long = 0,
    var upCount: Long = 0,
    var avgCount: Double = 0.0,
    var avgUpCount: Double = 0.0,
    var tag: String? = null,
    var hookAwards: List<HookAward> = ArrayList()
)

data class HookAward (
    var name: String? = null,
    var imageUri: String? = null,
    var isUp: Boolean = false,
    var count: Int = 0,
)