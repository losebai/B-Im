package com.example.myapplication.service

import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.util.HttpUtils
import com.example.myapplication.dto.Award
import com.example.myapplication.dto.TDict
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