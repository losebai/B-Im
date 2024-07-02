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


    var pools  by mutableStateOf(listOf<LotteryPool>(
        LotteryPool(0,"暂无",
            "https://mc.kurogames.com/static4.0/assets/news-bg-5e0dc97a.jpg",""
        )))

    // 模拟
    var award by mutableStateOf(listOf<Award>())
    private val appLotteryPoolService = AppLotteryPoolService()
    var lotteryAwardCountDto : LotteryAwardCountDto  = LotteryAwardCountDto()


    init {
        GlobalInitEvent.addUnit{
            pools = appLotteryPoolService.currentPools()
        }
    }

    fun currentPools(): List<LotteryPool> {
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

    fun lotteryAwardCount(isProd: Boolean = false) : LotteryAwardCountDto {
        return appLotteryPoolService.lotteryAwardCount(SystemApp.UserId, isProd);
    }

    fun asyncMcRecord(uri: String) : Map<String, Int> {
        return appLotteryPoolService.asyncMcRecord(SystemApp.UserId, uri);
    }
}