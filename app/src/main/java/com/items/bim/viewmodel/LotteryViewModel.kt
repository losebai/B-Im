package com.items.bim.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.items.bim.common.consts.SystemApp
import com.items.bim.dto.Award
import com.items.bim.dto.LotteryAwardCountDto
import com.items.bim.dto.LotteryPool
import com.items.bim.service.AppLotteryPoolService

class LotteryViewModel() : ViewModel() {



    // 模拟
    var award by mutableStateOf(listOf<Award>())
    private val appLotteryPoolService = AppLotteryPoolService()


    fun currentPools(gameName: String): List<LotteryPool> {
        return appLotteryPoolService.currentPools(gameName)
    }

    fun randomAward(gameName: String, catalogueId: Int,poolId: Int, num: Int=1, isUp :Boolean = false) : List<Award> {
        return appLotteryPoolService.randomAppAward(gameName,SystemApp.UserId, catalogueId, poolId, num, isUp)
    }

    fun lotteryAwardCount(gameName: String, userId: Long, isProd: Boolean = false) : LotteryAwardCountDto =
        appLotteryPoolService.lotteryAwardCount(gameName,userId, isProd);

    fun asyncMcRecord(gameName: String, uri: String) : Map<String, Int> {
        return appLotteryPoolService.asyncMcRecord(gameName, SystemApp.UserId, uri);
    }
}