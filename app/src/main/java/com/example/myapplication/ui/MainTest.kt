package com.example.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.dto.LotteryCount


@Composable
@Preview(showBackground = true)
fun LotterySimulateTest(){
    val lotteryMap: MutableMap<Int, List<LotteryCount>> = mutableMapOf()
    val l: List<LotteryCount> = listOf<LotteryCount>(
        LotteryCount(0, "https://prod-alicdn-community.kurobbs.com/forum/76b353a5c6484f56b6136ebf66f884c020240516.png",
            80, 0, false
        ),
        LotteryCount(0, "https://prod-alicdn-community.kurobbs.com/forum/76b353a5c6484f56b6136ebf66f884c020240516.png",
            23, 0, false
        ),
        LotteryCount(0, "https://prod-alicdn-community.kurobbs.com/forum/76b353a5c6484f56b6136ebf66f884c020240516.png",
            56, 0, true
        ),
        LotteryCount(0, "https://prod-alicdn-community.kurobbs.com/forum/76b353a5c6484f56b6136ebf66f884c020240516.png",
            11, 0, false
        )
    )
    lotteryMap[0] = l
    LotterySimulate(lotteryMap)
}