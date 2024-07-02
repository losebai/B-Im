package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.common.util.Utils
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.mc.dto.Handbook
import com.example.myapplication.event.GlobalInitEvent
import com.example.myapplication.mc.consts.LotteryPollEnum
import com.example.myapplication.mc.consts.MingChaoAPI
import com.example.myapplication.mc.dto.BannerDto
import com.example.myapplication.mc.dto.RoleBook
import com.example.myapplication.mc.dto.CatalogueDto
import com.example.myapplication.entity.McRecordEntity
import com.example.myapplication.mc.service.JueQuZeroService
import com.example.myapplication.mc.service.MingChaoService
import com.example.myapplication.mc.service.YuanShenService
import com.example.myapplication.repository.impl.OfflineUserRepository
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.repository.impl.OfflineMcRecordRepository
import com.example.myapplication.service.AbsToolService
import com.example.myapplication.service.DictService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class ToolsViewModel() : ViewModel() {

    private val mingChaoService = MingChaoService()
    private val yuanShenService = YuanShenService()
    private val jueQuZeroService = JueQuZeroService()

    val dictService = DictService()

    var catalogueName = ""
    var catalogueId = 0;
    val pool = arrayListOf<String>()

    private val logger = KotlinLogging.logger {
    }

    private var bannerMap = hashMapOf<String, List<BannerDto>>()


    var records = mutableStateMapOf<Int, List<McRecordEntity>>()

    private val mcRecordRepository: OfflineMcRecordRepository by lazy {
        OfflineMcRecordRepository(AppDatabase.getInstance().McRecordDao())
    }

    init {
        GlobalInitEvent.addUnit{
            pool.addAll(dictService.getKeyList("games").map { it.value })
            pool.forEach {
                bannerMap[it] = getToolService(it)?.getBannerList() ?: listOf()
                logger.info { "$it 一共获取了  ${bannerMap[it]?.size}" }
            }
        }
    }

    private fun getToolService(name: String) : AbsToolService? {
        return when(name) {
            "鸣潮" -> mingChaoService
            "原神" -> yuanShenService
            "绝区零" -> jueQuZeroService
            else -> {
                null
            }
        }
    }

    fun getBannerList(name: String) : List<BannerDto>{
        return bannerMap[name] ?: listOf()
    }

    fun getHandbook(type :Int) : List<Handbook>{
        return listOf()
    }

    fun getRoleBook(catalogueDto: CatalogueDto = CatalogueDto(MingChaoAPI.ROLE)): MutableList<RoleBook>{
        return mingChaoService.getRole(catalogueDto)
    }


    fun changeRecords(uri: String)  {
        val cs = CoroutineScope(Dispatchers.Default)
        Utils.message(cs, "获取抽奖记录已经在后台运行请勿重复提交", SystemApp.snackBarHostState)
        for (pool: LotteryPollEnum in LotteryPollEnum.entries){
            val list = mingChaoService.getGaChaRecord(uri, pool.value)
            records[pool.value] = list
            cs.launch(Dispatchers.Default){
                mcRecordRepository.insertItemBatch(list)
            }
        }
        Utils.message(cs, "获取抽奖记录完成，请到抽卡分析页面查看", SystemApp.snackBarHostState)
    }

}