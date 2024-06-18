package com.example.myapplication.service

import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.util.HttpUtils
import com.example.myapplication.dto.LotteryPool
import okhttp3.Response
import org.noear.snack.ONode
import java.util.Collections

class AppLotteryPoolService() {



    fun currentPools(): List<LotteryPool> {
        val res: Response? = HttpUtils.get(AppAPI.AppLotteryPoolAPI.CURRENT_POOLS)
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            return json["data"].toObjectList(LotteryPool::class.java)
        }
        return Collections.emptyList()
    }

}