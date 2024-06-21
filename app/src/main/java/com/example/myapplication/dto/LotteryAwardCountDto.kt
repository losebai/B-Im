package com.example.myapplication.dto

class LotteryAwardCountDto {
    var id: Long? = null
    var sumCount: Int = 0
    var star5Count: Int = 0
    var star4Count: Int = 0
    var avgUpCount: Double = 0.0
    var avgCount: Double = 0.0
    var poolLotteryAwardMap: Map<Int, PoolLotteryAward>? = null
    var userPoolLotteryAwards: List<UserPoolLotteryAward>? = null

    class PoolLotteryAward {
        var poolType: Int? = null
        var count = 0
        var avgCount = 0.0
        var Up = 0.0
        var tag: String? = null
        var hookAwards: List<HookAward> = ArrayList()
    }

    class UserPoolLotteryAward {
        var poolId: Int? = null
        var poolName: String? = null
        var count = 0
        var avgCount = 0.0
        var Up = 0.0
        var tag: String? = null
        var hookAwards: List<HookAward> = ArrayList()
    }

    class HookAward {
        var name: String? = null
        var imageUri: String? = null
        var isUp: Boolean? = null
        var count: Int? = null
    }
}
