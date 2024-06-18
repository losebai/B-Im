package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.dto.LotteryPollEnum
import com.example.myapplication.dto.LotteryPool
import com.example.myapplication.service.AppLotteryPoolService

class LotteryViewModel() : ViewModel() {

    private val appLotteryPoolService = AppLotteryPoolService()

    // 池子列表
    var pools = listOf<LotteryPool>(
        LotteryPool(0,"暂无",
                "https://web-static.kurobbs.com/resource/wiki/prod/assets/home-mc-bg-mobile-b993c3d5.png",""
        )
    )

    init {
        this.currentPools()
    }
    fun currentPools(): List<LotteryPool> {
        if (pools.size < 2){
            ThreadPoolManager.getInstance().addTask("lottery", "lottery"){
                pools = appLotteryPoolService.currentPools()
            }
        }
        return pools
    }
}