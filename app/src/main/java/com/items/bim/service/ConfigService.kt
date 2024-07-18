package com.items.bim.service

import com.items.bim.common.consts.AppAPI
import com.items.bim.common.util.HttpUtils
import okhttp3.Response
import org.noear.snack.ONode

class ConfigService {

    fun config() : ONode? {
        val res: Response? = HttpUtils.get(AppAPI.CONFIG_URI)
        if (res?.isSuccessful == true) {
            return ONode.load(res.body?.string())
        }
        return null
    }
}