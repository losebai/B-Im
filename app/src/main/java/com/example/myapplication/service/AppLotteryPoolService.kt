package com.example.myapplication.service

import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.util.HttpUtils
import com.example.myapplication.dto.Award
import com.example.myapplication.dto.LotteryAwardCountDto
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

    fun randomAppAward(userId :Long, catalogueId: Int,poolId: Int, num: Int=1,isUp :Boolean = false): List<Award> {
        val params: HashMap<String, Any> = hashMapOf()
        params["userId"] = userId
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

    fun lotteryAwardCount(userId: Long, isProd: Boolean) : LotteryAwardCountDto {
        val params: HashMap<String, Any> = hashMapOf()
        params["userId"] = userId
        params["isProd"] = isProd
        val res: Response? = HttpUtils.get( AppAPI.AppLotteryPoolAPI.LOTTERY_AWARD_COUNT, params)
        if (res?.isSuccessful == true){
            val str = res.body?.string()
            val json = ONode.load(str)
            return json["data"].toObject(LotteryAwardCountDto::class.java)
        }
        return LotteryAwardCountDto()
    }

    fun asyncMcRecord(userId: Long, uri: String) : Map<String, Int> {
        val params: HashMap<String, Any> = hashMapOf()
        params["userId"] = userId
        params["uri"] = uri
        val res: Response? = HttpUtils.get(
            AppAPI.AppLotteryPoolAPI.AWARD_ASYNC_RECORD,
            params,
        )
        if (res?.isSuccessful == true){
            val str = res.body?.string()
            val json = ONode.load(str)
            return json["data"].toObject(HashMap::class.java)
        }
        return mapOf()
    }

}