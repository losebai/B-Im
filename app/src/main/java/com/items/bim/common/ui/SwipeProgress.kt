package com.items.bim.common.ui

import androidx.compose.runtime.Immutable

const val NONE = 0

const val TOP = 1 //indicator在顶部

const val BOTTOM = 2 //indicator在底部

@Immutable
data class SwipeProgress(
    val location: Int = NONE,//是在顶部还是在底部
    val offset: Float = 0f,//可见indicator的高度
    /*@FloatRange(from = 0.0, to = 1.0)*/
    val fraction: Float = 0f //0到1，0： indicator不可见   1：可见indicator的最大高度
)

