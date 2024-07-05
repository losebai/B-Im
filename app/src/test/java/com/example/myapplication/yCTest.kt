package com.example.myapplication

import com.example.myapplication.mc.consts.YuanShenAPI
import com.example.myapplication.mc.service.YuanShenService
import org.junit.Test

class yCTest {

    val yuanShenService =  YuanShenService(YuanShenAPI())

    @Test
    fun bannerTest(){
        val list = yuanShenService.getBannerList()
        println(list)
    }
}