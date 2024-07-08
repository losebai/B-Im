package com.example.bim

import com.items.bim.service.AppLotteryPoolService
import org.junit.Test

class LotteryPoolTest {

    private val appLotteryPoolService = AppLotteryPoolService()


    @Test
    fun lotteryAwardCount(){
        val lotteryAwardCountDto =  appLotteryPoolService.lotteryAwardCount(3L,  false)
        println(lotteryAwardCountDto)
    }
}