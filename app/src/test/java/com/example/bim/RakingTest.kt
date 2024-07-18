package com.example.bim

import com.items.bim.common.consts.SystemApp
import com.items.bim.dto.GameRoleDto
import com.items.bim.service.RakingService
import org.junit.Test
import java.util.stream.Collectors

class RakingTest {

    val rakingService = RakingService()

    @Test
    fun test(){
        val appGameRole =  rakingService.getAppGameRole("鸣潮")
        val pools = appGameRole.appGameRoleRaking.keys.toList()
        pools.forEach{it ->
            appGameRole.appGameRoleRaking[it]?.stream()?.collect(
                Collectors.groupingBy(
                    GameRoleDto::raking))
        }

        println(appGameRole)
    }

    @Test
    fun getUserGameDto(){
        val user = rakingService.getUserGameDto(46, false, "鸣潮")
        println(user)
    }

    @Test
    fun getUserPoolRakingDto(){
        val map = rakingService.getUserPoolRakingDto(false, "鸣潮")
        println(map)
    }
}