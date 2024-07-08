package com.items.bim.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.items.bim.dto.LotteryCount
import com.items.bim.viewmodel.LotteryViewModel


@Composable
@Preview(showBackground = true)
fun LotterySimulateTest(){
    val lotteryMap: MutableMap<Int, List<LotteryCount>> = mutableMapOf()
    val l: List<LotteryCount> = listOf<LotteryCount>(
        LotteryCount(0, "https://prod-alicdn-community.kurobbs.com/forum/76b353a5c6484f56b6136ebf66f884c020240516.png",
            80, 0, false,false
        ),
        LotteryCount(0, "https://prod-alicdn-community.kurobbs.com/forum/76b353a5c6484f56b6136ebf66f884c020240516.png",
            23, 0, false, false
        ),
        LotteryCount(0, "https://prod-alicdn-community.kurobbs.com/forum/76b353a5c6484f56b6136ebf66f884c020240516.png",
            56, 0, true, false
        ),
        LotteryCount(0, "https://prod-alicdn-community.kurobbs.com/forum/76b353a5c6484f56b6136ebf66f884c020240516.png",
            11, 0, false, false
        )
    )
    lotteryMap[0] = l
    val lotteryViewModel = LotteryViewModel()
    LotterySimulate("鸣潮",44, lotteryViewModel)
}

@Composable
@Preview(showBackground = true)
fun Test(){
    val uri = "https://bbs-static.miyoushe.com/static/2024/07/04/e56eddc8df6bb047df44ed06f7fdda31_104566473761945531.png"
    AsyncImage(model = uri, contentDescription = null)
}