package com.items.bim.dto

data class UserGameDto(
    var name: String = "",
    var imageUri: String = "",
    var tag: String = "",
    var zonRaking: Int = 0,
    var uid: String = "",
    var sumCount: Int = 0,
    var star5Count: Int = 0,
    var UpCount: Int = 0,
    var avgUpCount: Double = 0.0,
    var avgCount: Double = 0.0,
)

/**
 * 用户排行
 */
data class UserPoolRakingDto(
    var userId: Long = 0,
    var name: String = "",
    var imageUri: String = "",
    var ouScore: Double = 0.0,
    var lotteryAwardCountDto: UserPoolLotteryAward = UserPoolLotteryAward()
)