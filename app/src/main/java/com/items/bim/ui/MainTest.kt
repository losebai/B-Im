package com.items.bim.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.items.bim.common.ui.LogCompositions
import com.items.bim.dto.LotteryCount
import com.items.bim.viewmodel.LotteryViewModel
import com.items.bim.viewmodel.ToolsViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds


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

@Composable
@Preview(showBackground = true)
fun Greeting() {
    var state by remember {
        mutableStateOf("Hi Foo")
    }
    Column {
        LogCompositions(msg = "Greeting Scope")
        Text(text = state)
        Button(
            onClick = { state = "Hi Foo ${Random.nextInt()}" },
            modifier = Modifier
                .padding(top = 32.dp)
        ) {
            LogCompositions(msg = "Button Scope")
            Text(
                text = "Click Me!"
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Test1(){
    var b by remember {
        mutableIntStateOf(0)
    }
    Column {
        Button(onClick = {
            b += 1
        }) {
            LogCompositions("Button")
            Text(text = "  $b")
        }
        Text(text = "  $b")
    }
    Log.d("Test", "外部被重组")
}

/**
 * compose作用域范围不是用remember来确定的，是采用非 inline fun 确定
 */
@Composable
@Preview(showBackground = true)
fun Test(){
    // Test 作用域
    var b by remember {
        mutableIntStateOf(0)
    }
    Column {
        LogCompositions("Column")
        Button(onClick = { b += 1 }) {
//            Wraper(b)
        }
        LogCompositions("Test : ${b}")
    }
    // Test 作用域
}


//@Composable
//fun Wraper(num: Int){
//    LogCompositions("Button ${num}")
//}

@Composable
@Preview(showBackground = true)
fun Test3(){
    // Test 作用域
    var b by remember {
        mutableIntStateOf(0)
    }
    Column {
        Button(onClick = { b += 1 }) {
            Text(text = "click")
        }
        Wraper(msg = {b})
        LogCompositions("Tes/${b}")
    }
    // Test 作用域
}


@Composable
fun Wraper(msg: () -> Int ){
    var data by remember {
        mutableIntStateOf(10)
    }
    Button(onClick = { data += msg() }) {
        Text(text = "  Wraper click")
    }
    LogCompositions("Wraper ${data}")
}

@Composable
@Preview(showBackground = true)
fun Test4(){
    var num by remember {
        mutableIntStateOf(0)
    }
    val isPositive by remember {
        derivedStateOf { num % 10 == 0 }
    }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(1.seconds)
            num += 1
        }
    }
    if (isPositive){
        Text(text = "$num")
    }
    LogCompositions("Test4")
}