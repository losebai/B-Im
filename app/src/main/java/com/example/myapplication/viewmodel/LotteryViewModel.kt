package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.dto.Award
import com.example.myapplication.dto.LotteryAwardCountDto
import com.example.myapplication.dto.LotteryCount
import com.example.myapplication.dto.LotteryPollEnum
import com.example.myapplication.dto.LotteryPool
import com.example.myapplication.event.GlobalInitEvent
import com.example.myapplication.service.AppLotteryPoolService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.concurrent.thread

class LotteryViewModel() : ViewModel() {


    private val logger = KotlinLogging.logger {
    }

    // 模拟
    var award by mutableStateOf(listOf<Award>())


    private val appLotteryPoolService = AppLotteryPoolService()

    var lotteryAwardCountDto = mutableStateOf(LotteryAwardCountDto())

    // 池子列表
    var pools = listOf<LotteryPool>(
        LotteryPool(0,"暂无",
                "https://web-static.kurobbs.com/resource/wiki/prod/assets/home-mc-bg-mobile-b993c3d5.png",""
        )
    )

    init {
        GlobalInitEvent.addUnit{
            currentPools()
        }
    }

    private fun currentPools(): List<LotteryPool> {
        if (pools.size < 2){
            ThreadPoolManager.getInstance().addTask("lottery", "lottery"){
                pools = appLotteryPoolService.currentPools()
            }
        }
        return pools
    }

    fun randomAward(catalogueId: Int,poolId: Int, num: Int=1, isUp :Boolean = false) : List<Award> {
        return appLotteryPoolService.randomAppAward(SystemApp.UserId, catalogueId, poolId, num, isUp)
    }

    fun lotteryAwardCount(isProd: Boolean = false) = thread {
        lotteryAwardCountDto.value = appLotteryPoolService.lotteryAwardCount(SystemApp.UserId, isProd);
    }
}