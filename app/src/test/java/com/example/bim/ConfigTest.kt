package com.example.bim

import com.items.bim.common.consts.ConfigKey
import com.items.bim.event.GlobalInitEvent
import com.items.bim.viewmodel.ConfigViewModel
import org.junit.Test

class ConfigTest {

    val configViewModel =  ConfigViewModel()


    @Test
    fun test(){
        GlobalInitEvent.run()
        val gameName = "鸣潮"
        val imageUri: String? = configViewModel.getConfig(ConfigKey.GAMES_BG.format(gameName), String::class.java)
        println(imageUri)
    }
}