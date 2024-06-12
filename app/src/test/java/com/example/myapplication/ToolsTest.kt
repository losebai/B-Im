package com.example.myapplication

import com.example.myapplication.service.MingChaoService
import org.junit.Test

class ToolsTest {

    @Test
    fun testHookList(){
        val mingChaoService = MingChaoService()
        val list = mingChaoService.getRole()
        println(list)
    }
}