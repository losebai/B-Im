package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.consts.ICons
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.ui.HeadImage
import com.example.myapplication.common.ui.MySwipeRefresh
import com.example.myapplication.common.ui.MySwipeRefreshState
import com.example.myapplication.common.ui.NORMAL
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.config.WEB_API_ROURE
import com.example.myapplication.dto.CommunityEntity
import com.example.myapplication.viewmodel.ToolsViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageBar(images: List<String>) {
    val pagerState = rememberPagerState {
        images.size
    }
    HorizontalPager(pagerState) {
        AsyncImage(model = images[it], contentDescription = null)
    }
}

@Composable
fun ImageTextList(
    communityEntity: CommunityEntity,
    modifier: Modifier = Modifier,
    isSend: Boolean = false
) {
    var pingLun by remember {
        mutableStateOf("")
    }
    var messageSend by remember {
        mutableStateOf(false)
    }
    val imageList = if (communityEntity.images.size > 3)
        communityEntity.images.subList(0, 2) else communityEntity.images
    Column(
        modifier = modifier
            .fillMaxSize()
            .border(1.dp, Color.Black)
            .background(Color.White),
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                HeadImage(
                    onClick = {},
                    userEntity = communityEntity.userEntity,
                    modifier = Modifier.size(50.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .height(100.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = communityEntity.userEntity.name, fontSize = 18.sp)
                    Text(text = communityEntity.createTime, fontSize = 14.sp)
                }
            }
            Text(
                text = communityEntity.title, modifier = Modifier
                    .padding(start = 10.dp), fontSize = 24.sp
            )
            Text(
                text = communityEntity.message, modifier = Modifier
                    .padding(start = 10.dp), fontSize = 16.sp
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(
                    when (communityEntity.images.size) {
                        1 -> 1
                        2 -> 2
                        else -> 3
                    }
                ),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                items(imageList.size) {
                    AsyncImage(
                        imageList[it],
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(100.dp)
                            .clickable {
                            }
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = "点赞"
                    )
                }
                if (isSend) {
                    IconButton(
                        onClick = { messageSend = !messageSend },
                        modifier = Modifier
                            .size(50.dp)
                            .padding(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "评论"
                        )
                    }
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "分享"
                    )
                }
            }
            if (isSend) {
                AnimatedVisibility(visible = messageSend) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        HeadImage(
                            onClick = {},
                            userEntity = communityEntity.userEntity,
                            modifier = Modifier
                                .height(40.dp)
                                .width(60.dp)
                                .padding(start = 10.dp, end = 10.dp)
                        )
                        OutlinedTextField(value = pingLun, modifier = Modifier
                            .width(300.dp)
                            .height(40.dp),
                            onValueChange = {
                                pingLun = it
                            })
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .height(40.dp)
                                .width(50.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = "发送"
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ImageText(list: List<CommunityEntity>, modifier: Modifier = Modifier) {
    val state by mutableStateOf(MySwipeRefreshState(NORMAL))
    MySwipeRefresh(state = state, onRefresh = { /*TODO*/ }, onLoadMore = { /*TODO*/ },
        modifier = modifier
    ) {
        LazyColumn {
            items(list) {
                ImageTextList(it)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ToolsList(
    toolsViewModel: ToolsViewModel,
    modifier: Modifier = Modifier,
    mainController: NavHostController = rememberNavController(),
) {
    val images = toolsViewModel.getImageBar(0)
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState {
        images.size
    }
    val pool = arrayOf("鸣潮", "原神", "表情库")
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(title = {
            LazyVerticalGrid(GridCells.Fixed(4), modifier = Modifier.fillMaxWidth()) {
                items(pool.size) {
                    Column(
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    pagerState.scrollToPage(it)
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = pool[it], fontSize = 20.sp, color= Color.Black)
                        Divider(
                            thickness = 2.dp,
                            color = if (pagerState.currentPage == it) Color.Green else Color.Transparent,
                        )
                    }
                }
            }
        },
            actions = {
                IconButton(onClick = {
                    mainController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "返回"
                    )
                }
            })

        HorizontalPager(pagerState, modifier = Modifier.fillMaxWidth()) {
            Column {
                Row {
                    if (images.size >= it) {
                        Surface(shape = StyleCommon.ONE_SHAPE) {
                            AsyncImage(
                                model = images[it], contentDescription = null,
                                modifier = Modifier
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                LazyVerticalGrid(
                    GridCells.Fixed(4),
                    modifier = Modifier.padding(5.dp)
                ) {
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = {
                                mainController.navigate(
                                    WEB_API_ROURE.WEB_ROUTE + "/${
                                        Uri.encode(
                                            AppAPI.MingChao.MAIN_URL
                                        )
                                    }"
                                )
                            }) {
                                AsyncImage(
                                    model = ICons.FAVICON,
                                    contentDescription = null,
                                    modifier = StyleCommon.ICON_SIZE
                                )
                            }
                            Text(text = "官网")
                        }
                    }
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = {
                                mainController.navigate(
                                    WEB_API_ROURE.WEB_ROUTE + "/${
                                        Uri.encode(
                                            AppAPI.MingChao.WIKI_URL
                                        )
                                    }"
                                )
                            }) {
                                AsyncImage(
                                    model = ICons.USER,
                                    contentDescription = "角色",
                                    modifier = StyleCommon.ICON_SIZE
                                )
                            }
                            Text(text = "我的")

                        }
                    }
                    item() {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = {
                                mainController.navigate(
                                    WEB_API_ROURE.WEB_ROUTE + "/${
                                        Uri.encode(
                                            AppAPI.MingChao.WIKI_URL
                                        )
                                    }"
                                )
                            }) {
                                AsyncImage(
                                    model = ICons.WIKI,
                                    contentDescription = "声骸图鉴",
                                    modifier = StyleCommon.ICON_SIZE
                                )
                            }
                            Text(text = "WIKI")
                        }
                    }
                    item() {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = {
                                mainController.navigate(
                                    PageRouteConfig.TOOLS_IMAGE_LIST
                                )
                            }) {
                                AsyncImage(
                                    model = ICons.WIKI,
                                    contentDescription = null,
                                    modifier = StyleCommon.ICON_SIZE
                                )
                            }
                            Text(text = "图片集合")
                        }
                    }
//                    item() {
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            IconButton(onClick = {
//                                mainController.navigate(
//                                    WEB_API_ROURE.WEB_ROUTE + "/${
//                                        Uri.encode(
//                                            AppAPI.MingChao.WIKI_URL
//                                        )
//                                    }"
//                                )
//                            }) {
//                                AsyncImage(
//                                    model = ICons.WIKI,
//                                    contentDescription = null,
//                                    modifier = StyleCommon.ICON_SIZE
//                                )
//                            }
//                            Text(text = "工具")
//                        }
//                    }
                }
                when (it) {
                    0 -> {
                        MingChaoHome(toolsViewModel, mainController)
                    }
                }
            }
        }
    }
}
