package com.items.bim.service

import com.items.bim.common.consts.AppAPI
import com.items.bim.common.util.HttpUtils
import com.items.bim.dto.TDict
import okhttp3.Response
import org.noear.snack.ONode
import java.util.Collections

class DictService {

    fun getKeyList(key: String): List<TDict> {
        val res: Response? = HttpUtils.get(AppAPI.DictAPI.GET_DICT_BY_KEYS, hashMapOf("key" to key))
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            return json["data"].toObjectList(TDict::class.java)
        }
        return Collections.emptyList()
    }
}