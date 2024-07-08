package com.example.bim

import com.items.bim.mc.consts.MingChaoAPI
import com.items.bim.mc.dto.CatalogueDto
import com.items.bim.mc.service.MingChaoService
import com.items.bim.service.DictService
import com.items.bim.service.FileService
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