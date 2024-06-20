package com.example.myapplication.service

import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.util.HttpUtils
import com.example.myapplication.dto.Award
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

    fun randomAppAward(catalogueId: Int,poolId: Int, num: Int=1,isUp :Boolean = false): List<Award> {
        val params: HashMap<String, Any> = hashMapOf()
        params["UserId"] = SystemApp.UserId
        params["catalogueId"] = catalogueId
        params["num"] = num
        params["isUp"] = if (isUp) "ture" else "false"
        params["poolId"] = poolId
        val res: Response? = HttpUtils.get(AppAPI.AppLotteryPoolAPI.RANDOM_AWARD, params)
        if (res?.isSuccessful == true){
            val json = ONode.load(res.body?.string())
            return json["data"].toObjectList(Award::class.java)
        }
        return Collections.emptyList()
    }

}