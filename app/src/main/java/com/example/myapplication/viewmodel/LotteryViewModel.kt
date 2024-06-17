package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.dto.LotteryPool

class LotteryViewModel() : ViewModel() {

    // 池子列表
    val pools = listOf<LotteryPool>()
}