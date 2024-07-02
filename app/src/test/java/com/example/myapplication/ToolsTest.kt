package com.example.myapplication

import com.example.myapplication.mc.dto.CatalogueDto
import com.example.myapplication.mc.service.MingChaoService
import com.example.myapplication.service.DictService
import org.junit.Test

class ToolsTest {

    @Test
    fun testHookList(){
        val mingChaoService = MingChaoService()
        val list = mingChaoService.getRole(CatalogueDto(1106))
        println(list)
    }

    @Test
    fun getKeyList(){
        val dictService = DictService()
        val list = dictService.getKeyList("games")
        println(list)
    }
}