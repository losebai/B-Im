package com.items.bim.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.items.bim.R
import com.items.bim.common.consts.StyleCommon
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.ui.HeadImage
import com.items.bim.common.ui.PagerList
import com.items.bim.common.ui.TopAppRow
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.config.PageRouteConfig
import com.items.bim.dto.UserGameDto
import com.items.bim.dto.UserPoolRakingDto
import com.items.bim.mc.consts.LotteryPollEnum
import com.items.bim.viewmodel.ToolsViewModel

@Composable
fun RankingUser(userProd: () -> UserGameDto, textColor: Color) {
    Row(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .padding(10.dp)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val user = userProd()
        HeadImage(path = SystemApp.USER_IMAGE, modifier = Modifier.size(80.dp))
        Column {
            Text(text = "   UID: ${user.uid}", color = textColor)
            Text(text = "   称号: ${user.tag}", color = textColor)
            Text(text = "   名称: ${user.name}", color = textColor)
        }
//            Text(text = user.zonRaking.toString(), color = color, fontSize = 30.sp)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RankingHome(
    gameNameValue: () -> String,
    toolsViewModel: ToolsViewModel,
    mainController: NavHostController
) {
    val pools = remember {
        LotteryPollEnum.entries.map { it.poolName }.toList()
    }
    var user by remember {
        mutableStateOf(UserGameDto())
    }
    var poolType by remember {
       mutableIntStateOf(0)
    }
    var isProd by remember {
        mutableStateOf(true)
    }
    var map by remember {
        mutableStateOf(hashMapOf<Int, ArrayList<UserPoolRakingDto>>())
    }
    val color = colorResource(id = R.color.golden)
    val textColor = Color.White
    LaunchedEffect(isProd) {
        ThreadPoolManager.getInstance().addTask("init", "getUserGameDto") {
            map = toolsViewModel.getUserPoolRakingDto( isProd, gameNameValue())
            user = toolsViewModel.getUserGameDto(isProd, gameNameValue())
            Log.d("rk", user.toString())
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.mc_lottery_bg),
                contentScale = ContentScale.Crop
            )
            .padding(20.dp)
    ) {
        TopAppRow(navigationIcon = {
            mainController.navigateUp()
        }, title = {
            Button(
                onClick = {
                    isProd = !isProd
                }, modifier = Modifier.padding(top = 30.dp, start = 10.dp),
                shape = StyleCommon.ONE_SHAPE,
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Text(text = if (!isProd) "切到到真实" else "切到到模拟", color = textColor)
            }
        })
        RankingUser(userProd = { user }, textColor)
        PagerList(pools = pools, textColor = Color.White) {
            poolType = it + 1
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                map[poolType]?.size?.let { it1 ->
                    items(it1) { index ->
                        val rakingDto = map[poolType]?.get(index)
                        if (rakingDto != null) {
                            key(rakingDto.userId) {
                                Row(
                                    modifier = Modifier
                                        .border(
                                            1.dp,
                                            if (rakingDto.userId == SystemApp.UserId) Color.White else Color.Black
                                        )
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = (index + 1).toString(),
                                        fontSize = 19.sp,
                                        color = if (index < 4) color else textColor
                                    )
                                    HeadImage(path = rakingDto.imageUri, modifier = Modifier.size(50.dp))
                                    Column(modifier = Modifier.fillMaxWidth(0.3f)) {
                                        Text(
                                            text = if (rakingDto.lotteryAwardCountDto.id == null) "未检测到" else rakingDto.lotteryAwardCountDto.id.toString(),
                                            color = textColor
                                        )
                                        Text(text = rakingDto.name, color = textColor)
                                    }
                                    Text(
                                        text = (rakingDto.ouScore * 100).toString(),
                                        fontSize = 18.sp,
                                        color = color
                                    )
                                    IconButton(onClick = {
                                        mainController.navigate("${PageRouteConfig.TOOLS_MINGCHAO_LOTTERY_DETAIL}/${rakingDto.userId}/${gameNameValue()}")
                                    }) {
                                        Icon(
                                            imageVector = Icons.Outlined.KeyboardArrowRight,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
