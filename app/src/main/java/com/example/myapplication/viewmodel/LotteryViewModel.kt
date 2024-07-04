package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.dto.Award
import com.example.myapplication.dto.LotteryAwardCountDto
import com.example.myapplication.dto.LotteryPool
import com.example.myapplication.event.GlobalInitEvent
import com.example.myapplication.service.AppLotteryPoolService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.concurrent.thread

class LotteryViewModel() : ViewModel() {



    // 模拟
    var award by mutableStateOf(listOf<Award>())
    private val appLotteryPoolService = AppLotteryPoolService()


    fun currentPools(): List<LotteryPool> {
        return appLotteryPoolService.currentPools()
    }

    fun randomAward(catalogueId: Int,poolId: Int, num: Int=1, isUp :Boolean = false) : List<Award> {
        return appLotteryPoolService.randomAppAward(SystemApp.UserId, catalogueId, poolId, num, isUp)
    }

    fun lotteryAwardCount(userId: Long, isProd: Boolean = false) : LotteryAwardCountDto =
        appLotteryPoolService.lotteryAwardCount(userId, isProd);

    fun asyncMcRecord(uri: String) : Map<String, Int> {
        return appLotteryPoolService.asyncMcRecord(SystemApp.UserId, uri);
    }
}