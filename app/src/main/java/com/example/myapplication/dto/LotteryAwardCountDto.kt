package com.example.myapplication.dto

data class LotteryAwardCountDto (
    var id: Long? = null,
    var name: String = "",
    var sumCount: Int = 0,
    var star5Count: Int = 0,
    var star4Count: Int = 0,
    var avgRoleCount: Double = 0.0,
    var avgWeaponCount: Double = 0.0,
    var up: Double = 0.0,
    var upCount: Int = 0,
    var avgCount: Double = 0.0,
    var tag: String? = null,
    // 池子类型
    var poolLotteryAwardMap: Map<Int, PoolLotteryAward> = hashMapOf(),

    // 池子
    var userPoolLotteryAwards: List<UserPoolLotteryAward> = arrayListOf(),
)

data class PoolLotteryAward (
    var poolType: Int? = null,
    var count : Int= 0,
    var avgCount : Double= 0.0,
    var up : Double= 0.0,
    var tag: String? = null,
    var hookAwards: List<HookAward> = ArrayList()
)

data class UserPoolLotteryAward (
    val imageUri: String? = null,
    var poolId: Int? = null,
    var poolName: String? = null,
    var count: Int = 0,
    var avgCount: Double = 0.0,
    var up: Double = 0.0,
    var tag: String? = null,
    var hookAwards: List<HookAward> = ArrayList()
)

data class HookAward (
    var name: String? = null,
    var imageUri: String? = null,
    var isUp: Boolean = false,
    var count: Int = 0,
)