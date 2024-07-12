package com.example.bim

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.items.bim.dto.LotteryCount
import com.items.bim.ui.LotterySimulate
import com.items.bim.ui.ToolsList
import com.items.bim.viewmodel.LotteryViewModel
import com.items.bim.viewmodel.ToolsViewModel


@Composable
@Preview(showBackground = true)
fun ToolsListTest(){
    ToolsList(ToolsViewModel())
}

@Composable
@Preview(showBackground = true)
fun LotterySimulateTest(){
    val lotteryMap: MutableMap<Int, List<LotteryCount>> = mutableMapOf()
    val l: List<LotteryCount> = listOf()
    l.plus(LotteryCount(0, "https://prod-alicdn-community.kurobbs.com/forum/76b353a5c6484f56b6136ebf66f884c020240516.png",
        80, 0, true,true
    ))
    l.plus(LotteryCount(0, "https://prod-alicdn-community.kurobbs.com/forum/76b353a5c6484f56b6136ebf66f884c020240516.png",
        20, 0, false,false
    ))
    l.plus(LotteryCount(0, "https://prod-alicdn-community.kurobbs.com/forum/76b353a5c6484f56b6136ebf66f884c020240516.png",
        40, 0, true,false
    ))
    lotteryMap[0] = l
    val lotteryViewModel: LotteryViewModel = LotteryViewModel()
    LotterySimulate("鸣潮",44, lotteryViewModel)
}