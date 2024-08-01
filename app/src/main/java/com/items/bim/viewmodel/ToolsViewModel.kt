package com.items.bim.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.common.util.Utils
import com.items.bim.database.AppDatabase
import com.items.bim.dto.AppGameRole
import com.items.bim.dto.UserPoolRakingDto
import com.items.bim.entity.McRecordEntity
import com.items.bim.event.GlobalInitEvent
import com.items.bim.mc.consts.BaseAPI
import com.items.bim.mc.consts.JueQuZeroAPI
import com.items.bim.mc.consts.LotteryPollEnum
import com.items.bim.mc.consts.MingChaoAPI
import com.items.bim.mc.consts.TieDaoAPI
import com.items.bim.mc.consts.YuanShenAPI
import com.items.bim.mc.dto.BannerDto
import com.items.bim.mc.dto.CatalogueDto
import com.items.bim.mc.dto.Handbook
import com.items.bim.mc.dto.RoleBook
import com.items.bim.mc.service.JueQuZeroService
import com.items.bim.mc.service.MingChaoService
import com.items.bim.mc.service.TieDaoService
import com.items.bim.mc.service.YuanShenService
import com.items.bim.repository.impl.OfflineMcRecordRepository
import com.items.bim.service.AbsToolService
import com.items.bim.service.DictService
import com.items.bim.service.RakingService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    var appGameRole by mutableStateOf(AppGameRole())

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

    fun getUserPoolRakingDto(isProd: Boolean, gameName: String)=
        rakingService.getUserPoolRakingDto(isProd, gameName)

    fun getUserGameDto(isProd: Boolean,  gameName: String)
    = rakingService.getUserGameDto(SystemApp.UserId, isProd, gameName)

    fun getAppGameRole(gameName: String){
        ThreadPoolManager.getInstance().addTask("init", "GameRoleRaking") {
            appGameRole =  rakingService.getAppGameRole(gameName);
        }
    }
}