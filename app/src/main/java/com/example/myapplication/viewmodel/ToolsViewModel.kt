package com.example.myapplication.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.mc.dto.Handbook
import com.example.myapplication.dto.LotteryCount
import com.example.myapplication.event.GlobalInitEvent
import com.example.myapplication.mc.consts.MingChaoAPI
import com.example.myapplication.mc.dto.BannerDto
import com.example.myapplication.mc.dto.RoleBook
import com.example.myapplication.mc.dto.CatalogueDto
import com.example.myapplication.mc.dto.HomeDto
import com.example.myapplication.mc.dto.ToolHomeDto
import com.example.myapplication.mc.service.MingChaoService
import com.example.myapplication.service.AbsToolService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.concurrent.thread

class ToolsViewModel() : ViewModel() {

    private val mingChaoService = MingChaoService()

    var catalogueName = ""
    var catalogueId = 0;
    val pool = arrayOf("鸣潮")

    private val logger = KotlinLogging.logger {
    }

    private var bannerMap = hashMapOf<String, List<BannerDto>>()

    init {
        GlobalInitEvent.addUnit{
            pool.forEach {
                bannerMap[it] = getToolService(it).getBannerList()
                logger.info { "一共获取了  ${bannerMap[it]?.size}" }
            }
        }
    }

    private fun getToolService(name: String) : AbsToolService{
        return when(name) {
            pool[0] -> mingChaoService
            else -> {
                mingChaoService
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

}