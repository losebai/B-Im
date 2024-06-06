package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.dto.LotteryCount

class ToolsViewModel() : ViewModel() {

    // 抽奖卡池
    val lotteryMap: MutableMap<Int, List<LotteryCount>> = mutableMapOf()


    fun getImageBar(int: Int) :List<String>{
        return listOf()
    }

}