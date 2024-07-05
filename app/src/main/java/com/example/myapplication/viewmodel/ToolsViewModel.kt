package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.common.util.Utils
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.dto.UserPoolRakingDto
import com.example.myapplication.entity.McRecordEntity
import com.example.myapplication.event.GlobalInitEvent
import com.example.myapplication.mc.consts.BaseAPI
import com.example.myapplication.mc.consts.JueQuZeroAPI
import com.example.myapplication.mc.consts.LotteryPollEnum
import com.example.myapplication.mc.consts.MingChaoAPI
import com.example.myapplication.mc.consts.TieDaoAPI
import com.example.myapplication.mc.consts.YuanShenAPI
import com.example.myapplication.mc.dto.BannerDto
import com.example.myapplication.mc.dto.CatalogueDto
import com.example.myapplication.mc.dto.Handbook
import com.example.myapplication.mc.dto.RoleBook
import com.example.myapplication.mc.service.JueQuZeroService
import com.example.myapplication.mc.service.MingChaoService
import com.example.myapplication.mc.service.TieDaoService
import com.example.myapplication.mc.service.YuanShenService
import com.example.myapplication.repository.impl.OfflineMcRecordRepository
import com.example.myapplication.service.AbsToolService
import com.example.myapplication.service.DictService
import com.example.myapplication.service.RakingService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToolsViewModel() : ViewModel() {

    private val jueQuZeroAPI = JueQuZeroAPI()
    private val yuanShenAPI = YuanShenAPI()
    private val mingChaoAPI = MingChaoAPI()
    private val tieDaoAPI = TieDaoAPI()

    private val mingChaoService by  lazy {
        MingChaoService(mingChaoAPI)
    }
    private val yuanShenService by lazy {
        YuanShenService(yuanShenAPI)
    }
    private val jueQuZeroService by lazy {
        JueQuZeroService(jueQuZeroAPI)
    }

    private val tieDaoService by lazy {
        TieDaoService(tieDaoAPI)
    }

    private val rakingService = RakingService()


    val dictService = DictService()

    var catalogueName = ""
    var catalogueId = 0;
    val pool = arrayListOf<String>()

    private val logger = KotlinLogging.logger {
    }

    private var bannerMap = hashMapOf<String, List<BannerDto>>()


    private var records = mutableStateMapOf<Int, List<McRecordEntity>>()

    private val mcRecordRepository: OfflineMcRecordRepository by lazy {
        OfflineMcRecordRepository(AppDatabase.getInstance().McRecordDao())
    }

    init {
        GlobalInitEvent.addUnit {
            pool.addAll(dictService.getKeyList("games").map { it.value })
            pool.forEach {
                bannerMap[it] = getToolService(it)?.getBannerList() ?: listOf()
                logger.info { "$it 一共获取了  ${bannerMap[it]?.size}" }
            }
        }
    }

    private fun getToolService(name: String): AbsToolService? {
        return when (name) {
            "鸣潮" -> mingChaoService
            "原神" -> yuanShenService
            "绝区零" -> jueQuZeroService
            "星穹铁道" -> tieDaoService
            else -> {
                null
            }
        }
    }

    fun getBaseAPI(name: String): BaseAPI {
        return when (name) {
            "鸣潮" -> mingChaoService.mingChaoAPI
            "原神" -> yuanShenService.yuanShenAPI
            "绝区零" -> jueQuZeroService.jueQuZeroAPI
            "星穹铁道" -> tieDaoService.tieDaoAPI
            else -> {
                mingChaoService.mingChaoAPI
            }
        }
    }


    fun getBannerList(name: String): List<BannerDto> {
        if (!bannerMap.contains(name)){
            ThreadPoolManager.getInstance().addTask("init", name){
                bannerMap[name] = getToolService(name)?.getBannerList() ?: listOf()
            }
        }
        return bannerMap[name] ?: listOf()
    }

    fun getHandbook(type: Int): List<Handbook> {
        return listOf()
    }

    fun getRoleBook(catalogueDto: CatalogueDto = CatalogueDto(MingChaoAPI.ROLE)): MutableList<RoleBook> {
        return mingChaoService.getRole(catalogueDto)
    }


    fun changeRecords(uri: String) {
        val cs = CoroutineScope(Dispatchers.Default)
        Utils.message(cs, "获取抽奖记录已经在后台运行请勿重复提交", SystemApp.snackBarHostState)
        for (pool: LotteryPollEnum in LotteryPollEnum.entries) {
            val list = mingChaoService.getGaChaRecord(uri, pool.value)
            records[pool.value] = list
            cs.launch(Dispatchers.Default) {
                mcRecordRepository.insertItemBatch(list)
            }
        }
        Utils.message(cs, "获取抽奖记录完成，请到抽卡分析页面查看", SystemApp.snackBarHostState)
    }

    fun getUserPoolRakingDto(poolType: Int, isProd: Boolean, gameName: String): List<UserPoolRakingDto> =
        rakingService.getUserPoolRakingDto(poolType, isProd, gameName)

    fun getUserGameDto(isProd: Boolean,  gameName: String)
    = rakingService.getUserGameDto(SystemApp.UserId, isProd, gameName)
}