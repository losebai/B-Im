package com.example.myapplication

import com.example.myapplication.mc.consts.MingChaoAPI
import com.example.myapplication.mc.dto.HomeDto
import com.example.myapplication.mc.service.MingChaoService
import org.junit.Test

class MingCahoTest {

    private val mingChaoService = MingChaoService(MingChaoAPI())

    @Test
    fun homePageTest(){
        val home : HomeDto = mingChaoService.homePage()
        println(home)
    }

    @Test
    fun getRecord(){
        val uri = "https://aki-gm-resources.aki-game.com/aki/gacha/index.html#/record?svr_id=76402e5b20be2c39f095a152090afddc&player_id=112773177&gacha_id=200001&gacha_type=2&svr%20area=cn&record_id=54a459ab63757e4984e70254db6a1995&resources_id=1&cvid=6b66c9a9a7a546ad951e549795049eac&gs_lcrp=EgZjaHJvbWUyBggAEEUYOTIGCAEQRRg60gEINzA5OWowajSoAgCwAgA&FORM=ANAB01&adppc=EdgeStart&PC=LCTS";
        val list = mingChaoService.getGaChaRecord(uri, 1)
        list.forEach{
            println(it)
        }
    }
}