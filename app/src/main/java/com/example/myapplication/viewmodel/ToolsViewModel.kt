package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.dto.Handbook
import com.example.myapplication.dto.LotteryCount
import com.example.myapplication.dto.RoleBook
import com.example.myapplication.service.MingChaoService

class ToolsViewModel() : ViewModel() {

    // 抽奖卡池
    val lotteryMap: MutableMap<Int, List<LotteryCount>> = mutableMapOf()

    val roleBooks : List<RoleBook> = mutableListOf()

    val mingChaoService = MingChaoService()


    fun getImageBar(int: Int) :List<String>{
        return listOf("https://prod-alicdn-community.kurobbs.com/forum/db6e35e29de04d7b842e69a917bfb36d20240522.jpg",
            "https://bbs-static.miyoushe.com/static/2024/06/03/d83ea7f72a54e4db39dfd12f10d08eb3_8340800214612498828.jpg"
            )
    }

    fun getHandbook(type :Int) : List<Handbook>{
        return listOf()
    }

    fun getRoleBook(): MutableList<RoleBook>{
        return mingChaoService.getRole()
    }
}