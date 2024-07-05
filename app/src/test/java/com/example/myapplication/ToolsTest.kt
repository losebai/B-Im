package com.example.myapplication

import com.example.myapplication.mc.consts.MingChaoAPI
import com.example.myapplication.mc.dto.CatalogueDto
import com.example.myapplication.mc.service.MingChaoService
import com.example.myapplication.service.DictService
import com.example.myapplication.service.FileService
import org.junit.Test

class ToolsTest {

    @Test
    fun testHookList(){
        val mingChaoService = MingChaoService(MingChaoAPI())
        val list = mingChaoService.getRole(CatalogueDto(1106))
        println(list)
    }

    @Test
    fun getKeyList(){
        val dictService = DictService()
        val list = dictService.getKeyList("games")
        println(list)
    }

    @Test
    fun uploadTest(){
        val path = FileService.uploadImage("D:\\java_items\\images\\img_1.png")
        val url = FileService.getImageUrl(path)
        println(url)
    }
}