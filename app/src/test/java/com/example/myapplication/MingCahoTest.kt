package com.example.myapplication

import com.example.myapplication.mc.dto.HomeDto
import com.example.myapplication.mc.service.MingChaoService
import org.junit.Test

class MingCahoTest {

    private val mingChaoService = MingChaoService()

    @Test
    fun homePageTest(){
        val home : HomeDto = mingChaoService.homePage()
        println(home)
    }
}