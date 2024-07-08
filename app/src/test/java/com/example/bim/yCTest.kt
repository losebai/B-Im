package com.example.bim

import com.items.bim.mc.consts.YuanShenAPI
import com.items.bim.mc.service.YuanShenService
import org.junit.Test

class yCTest {

    val yuanShenService =  YuanShenService(YuanShenAPI())

    @Test
    fun bannerTest(){
        val list = yuanShenService.getBannerList()
        println(list)
    }
}