package com.items.bim.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.items.bim.common.ui.LogCompositions
import com.items.bim.dto.LotteryCount
import com.items.bim.viewmodel.LotteryViewModel
import com.items.bim.viewmodel.ToolsViewModel
import io.github.oshai.kotlinlogging.KotlinLogging


private val logger = KotlinLogging.logger {}

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
    LotterySimulate("鸣潮",44,  lotteryViewModel, ToolsViewModel())
}

@Composable
fun NotText(text: () -> String){
    LogCompositions("NotText")
    Text(text = text())

    Test()
}


/**
 * compose作用域范围不是用remember来确定的，是采用非 inline fun 确定
 */
@Composable
@Preview(showBackground = true)
fun Test(){
    Row {
        var b by remember {
            mutableStateOf(false)
        }
        Button(onClick = {
            b = !b
        }) {
            LogCompositions("Button")
            Text(text = b.toString())
            logger.info { b.toString() }
        }
    }
    Column {
        Text(text = "11")
        LogCompositions("Column")
    }
    LogCompositions("Main Test")
}