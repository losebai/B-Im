package com.items.bim.viewmodel

import androidx.lifecycle.ViewModel
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.event.GlobalInitEvent
import com.items.bim.service.ConfigService
import org.noear.snack.ONode

class ConfigViewModel : ViewModel() {

    private val configService = ConfigService()

    private var config: ONode? = null

    init {
        GlobalInitEvent.addUnit {
            config = configService.config()
        }
    }

    fun check() {
        if (config == null) {
            config = configService.config()
        }
    }

    fun <T> getConfig(key: String, clazz: Class<T>): T? {
        return config?.select("$.${key}")?.toObject(clazz)
    }

}