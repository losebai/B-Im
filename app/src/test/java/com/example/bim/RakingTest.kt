package com.example.bim

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
}