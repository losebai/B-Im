package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.provider.PlayerProvider
import com.example.myapplication.common.ui.VideScreen
import com.example.myapplication.dto.LotteryPool
import com.example.myapplication.viewmodel.LotteryViewModel
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {
}


@Composable
fun MCRoleLotteryHome(
    lotteryViewModel: LotteryViewModel,
    onLottery: (LotteryPool, Int) -> Unit = { l, i -> },
    onDispatch: () -> Unit = {}
) {
    var poolIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize(),
    ) {
        AsyncImage(
            LotteryViewModel.pools[poolIndex].poolBg,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
        Row(
            modifier =
            Modifier.padding(start = 0.dp, top = 20.dp, bottom = 20.dp, end = 60.dp)
        ) {
            Column(
                Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(0.15f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn(
                    Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(LotteryViewModel.pools.size) {
                        AsyncImage(
                            model = LotteryViewModel.pools[it].poolImageUri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
//                                .fillMaxSize()
                                .height(if (poolIndex == it) 50.dp else 40.dp)
                                .width(if (poolIndex == it) 100.dp else 90.dp)
                                .padding(top = 5.dp)
                                .border(1.dp, Color.White)
                                .clickable {
                                    poolIndex = it
                                }
                        )
                    }
                }
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "意见反馈", color = Color.White, fontSize = 10.sp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(text = "免责声明", color = Color.White, fontSize = 10.sp)
                }
            }
            Divider(
                color = Color.White,
                thickness = 5.dp,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp, top = 40.dp, bottom = 40.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "角色活动唤取", color = Color.Yellow, fontSize = 10.sp)
                    Text(
                        text = LotteryViewModel.pools[poolIndex].poolName,
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
                Column {
                    Text(text = "以下四星概率提升", color = Color.White, fontSize = 10.sp)
                    LazyRow {
                        items(LotteryViewModel.pools[poolIndex].array.size) {
                            Column(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .border(1.dp, Color.Gray, StyleCommon.ONE_SHAPE)
                            ) {
                                AsyncImage(
                                    model = LotteryViewModel.pools[poolIndex].array[it],
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Black,
                                                    colorResource(id = R.color.star4)
                                                ),
                                                startY = 50f,
                                                endY = 150f,
                                                tileMode = TileMode.Clamp
                                            )
                                        )
                                )
                                Divider(
                                    color = colorResource(id = R.color.star4),
                                    thickness = 3.dp,
                                    modifier = Modifier
                                        .width(40.dp)
                                )
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Box(modifier = Modifier.clickable {
                    onLottery(LotteryViewModel.pools[poolIndex], 1)
                }, contentAlignment = Alignment.CenterStart) {
                    Image(
                        painter = painterResource(id = R.drawable.mc_icons), null,
                        modifier = Modifier.padding(start = 0.dp)
                    )
                    AsyncImage(
                        model = stringResource(id = R.string.mc_role_tick),
                        modifier = Modifier
                            .size(20.dp)
                            .offset(x = 20.dp),
                        contentDescription = null
                    )
                }
                Box(modifier = Modifier.clickable {
                    onLottery(LotteryViewModel.pools[poolIndex], 10)
                }, contentAlignment = Alignment.CenterStart) {
                    Image(
                        painter = painterResource(id = R.drawable.hhobfg3k), null,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    AsyncImage(
                        model = stringResource(id = R.string.mc_role_tick),
                        modifier = Modifier
                            .size(20.dp)
                            .offset(x = 35.dp),
                        contentDescription = null
                    )
                }
            }

            IconButton(
                onClick = onDispatch,
                modifier = Modifier
                    .width(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mc_08o2pbcd), null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

val playerProvider = PlayerProvider.getInstance().let {
    it.onClose = {

    }
}

@Composable
fun LotteryVideo(videoUri: String, onClose: () -> Unit) {
    VideScreen(videoUri)
}


@Composable
fun AwardList(
    modifier: Modifier = Modifier, lotteryViewModel: LotteryViewModel,
    onDispatch: () -> Unit = {}
) {

    val list = lotteryViewModel.award
    Box(contentAlignment = Alignment.Center) {
        AsyncImage(
            model = "https://mc.kurogames.com/static4.0/assets/news-bg-5e0dc97a.jpg",
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight()
                    .padding(start = 40.dp, top = 20.dp, bottom = 20.dp, end = 20.dp)
            ) {
                items(list.size) {
                    val color = colorResource(
                        id = when (list[it].star) {
                            4 -> R.color.star4
                            5 -> R.color.star5
                            else -> {
                                R.color.star3
                            }
                        }
                    )
                    Column(
                        modifier =
                        Modifier
                            .padding(top = 20.dp, bottom = 10.dp, start = 5.dp, end = 5.dp)
                            .border(if (list[it].star == 5) 2.dp else 0.dp, color)
                            .paint(
                                painterResource(
                                    id = when (list[it].star) {
                                        4 -> R.drawable.mc_start4_bg
                                        5 -> R.drawable.mc_start5_bg
                                        else -> {
                                            R.drawable.mc_start3_bg
                                        }
                                    }
                                ), contentScale = ContentScale.FillBounds
                            ),
                    ) {
                        AsyncImage(
                            model = list[it].imageUri,
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black,
                                            color
                                        ),
                                        startY = 250f,
                                        endY = 400f,
                                        tileMode = TileMode.Clamp
                                    )
                                )
                        )
                        Divider(
                            color = color,
                            thickness = 3.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
            Divider(
                color = Color.White,
                thickness = 5.dp,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .width(1.dp)
            )
            Column(modifier = Modifier.fillMaxHeight()) {
                IconButton(
                    onClick = onDispatch,
                    modifier = Modifier
                        .padding(20.dp)
                        .width(50.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mc_08o2pbcd), null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
