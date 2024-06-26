package com.example.myapplication

import com.example.myapplication.mc.dto.CatalogueDto
import com.example.myapplication.mc.service.MingChaoService
import com.example.myapplication.service.AppLotteryPoolService
import org.junit.Test

class LotteryPoolTest {

    private val appLotteryPoolService = AppLotteryPoolService()


    @Test
    fun lotteryAwardCount(){
        val lotteryAwardCountDto =  appLotteryPoolService.lotteryAwardCount(3L)
        println(lotteryAwardCountDto)
    }
}