package com.items.bim.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.items.bim.R
import com.items.bim.activity.LotteryActivity
import com.items.bim.common.consts.StyleCommon
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.provider.BaseContentProvider
import com.items.bim.common.ui.MySwipeRefresh
import com.items.bim.common.ui.MySwipeRefreshState
import com.items.bim.common.ui.NORMAL
import com.items.bim.common.ui.PagerList
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.common.util.Utils
import com.items.bim.config.MingChaoRoute
import com.items.bim.config.PageRouteConfig
import com.items.bim.dto.LotteryAwardCountDto
import com.items.bim.mc.consts.BaseAPI
import com.items.bim.mc.dto.CatalogueDto
import com.items.bim.mc.dto.RoleBook
import com.items.bim.viewmodel.LotteryViewModel
import com.items.bim.viewmodel.ToolsViewModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope

private val logger = KotlinLogging.logger {
}

@Composable
fun LotterySimulateHead(gameName : String,
                        textColor: Color,
                        isProd: MutableState<Boolean>,
                        mainController: NavHostController = rememberNavController()){
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        IconButton(onClick = {
            mainController.navigateUp()
        }, modifier = Modifier.padding(top = 30.dp, start = 10.dp)) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "返回",
                tint = Color.White
            )
        }
        Button(
            onClick = {
                isProd.value = !isProd.value
            }, modifier = Modifier.padding(top = 30.dp, start = 10.dp),
            shape = StyleCommon.ONE_SHAPE,
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Text(text = if (!isProd.value) "切到到真实" else "切到到模拟", color = textColor)
        }
        Button(
            onClick = {
                mainController.navigate("${MingChaoRoute.SET_COOKIES}/${gameName}")
            }, modifier = Modifier.padding(top = 30.dp, start = 10.dp),
            shape = StyleCommon.ONE_SHAPE,
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Text(text = "设置", color = textColor)
        }
    }
}

@Composable
fun LotterySimulateHeadInfo( lotteryAwardCountDto : () -> LotteryAwardCountDto,
                            textColor: Color,
                            isProd: MutableState<Boolean>,
                          ){
    val rowModifier = Modifier.fillMaxWidth()
    val columModifier = Modifier.height(50.dp)
    val color = colorResource(id = R.color.star5)
    Card(
        Modifier
            .height(340.dp)
            .padding(20.dp)
            .border(1.dp, Color.White)
            .padding(20.dp),
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = if (isProd.value) "当前真实卡池" else "当前模拟卡池",
                color = textColor,
                fontSize = 18.sp
            )
        }
        Text(text = "UID: ${lotteryAwardCountDto().id}", color = textColor)
        Row(
            modifier = rowModifier,
        ) {
            Text(
                text = lotteryAwardCountDto().tag ?: "兄弟，你不行诶",
                color = color,
                fontSize = 25.sp,
            )
        }
        Row(
            modifier = rowModifier,
        ) {
            Text(text = "总次数: ", color = textColor)
            Text(
                text = lotteryAwardCountDto().sumCount.toString(),
                color = color,
                fontSize = 20.sp
            )
        }
        Row(
            modifier = rowModifier,
        ) {
            Text(text = "平均出金: ", color = textColor)
            Text(
                text = lotteryAwardCountDto().avgCount.toString(),
                color = color,
                fontSize = 20.sp
            )
        }
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                columModifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Up数量: ", color = textColor)
                Text(
                    text = lotteryAwardCountDto().upCount.toString(),
                    color = color,
                    fontSize = 20.sp
                )
            }
            Column(
                columModifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "五星数量: ", color = textColor)
                Text(
                    text = lotteryAwardCountDto().star5Count.toString(),
                    color = color,
                    fontSize = 20.sp
                )
            }
            Column(
                columModifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "四星数量: ", color = textColor)
                Text(
                    text = lotteryAwardCountDto().star4Count.toString(),
                    color = color,
                    fontSize = 20.sp
                )
            }
        }
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                columModifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "小保底不歪: ", color = textColor)
                Text(
                    text = lotteryAwardCountDto().up.toString(),
                    color = color,
                    fontSize = 20.sp
                )
            }
            Column(
                columModifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "每UP出金: ", color = textColor)
                Text(
                    text = lotteryAwardCountDto().avgUpCount.toString(),
                    color = color,
                    fontSize = 20.sp
                )
            }
            Column(
                columModifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "每UP武器: ", color = textColor)
                Text(
                    text = lotteryAwardCountDto().avgWeaponCount.toString(),
                    color = color,
                    fontSize = 20.sp
                )
            }
        }
    }
}

/**
 * 加载抽卡分析
 * @param [lotteryMap] 彩票地图
 * @param [mainController] 主控制器
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LotterySimulate(
    gameName: String,
    userId: Long,
    lotteryViewModel: LotteryViewModel,
    toolsViewModel: ToolsViewModel,
    mainController: NavHostController = rememberNavController()
) {
    val color = colorResource(id = R.color.golden)
    var lotteryAwardCountDto by remember {
        mutableStateOf(LotteryAwardCountDto())
    }
    val poolLotteryAwardsisNotEmpty by remember {
        derivedStateOf { lotteryAwardCountDto.poolLotteryAwards.isNotEmpty() }
    }
    val upColor = colorResource(id = R.color.star5)
    val textColor = Color.White
    val rowModifier = Modifier.fillMaxWidth()
    val isProd = remember {
        mutableStateOf(false)
    }
    val api = toolsViewModel.getBaseAPI(gameName)
    LaunchedEffect(isProd.value) {
        ThreadPoolManager.getInstance().addTask("init", "lotteryAwardCountDto") {
            lotteryAwardCountDto = lotteryViewModel.lotteryAwardCount(gameName, userId, isProd.value)
            logger.info { "LaunchedEffect 开始加载抽卡分析 ： $isProd" }
        }
    }
    logger.info { "加载抽卡分析" }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = api.bg_id),
                contentScale = ContentScale.Crop
            )
    ) {
        item {
            LotterySimulateHead(gameName ,textColor, isProd, mainController)
        }
        item {
            LotterySimulateHeadInfo(lotteryAwardCountDto={lotteryAwardCountDto}, textColor, isProd)
        }

        if (poolLotteryAwardsisNotEmpty) {
            item {
                Column(
                    modifier = Modifier
                        .height(310.dp)
                        .padding(20.dp)
                        .border(1.dp, Color.White)
                        .padding(20.dp)
                ) {
                    val poolList =
                        lotteryAwardCountDto.poolLotteryAwards.filter { it.poolName.isNotEmpty() }
                    PagerList(
                        Modifier.fillMaxWidth(),
                        pools = poolList.map { it.poolName }.toList(),
                        textColor = textColor
                    ) {
                        LazyColumn(
                            Modifier.height(310.dp),
                            reverseLayout = true,
                        ) {
                            val poolLotteryAward = poolList[it]
                            items(poolLotteryAward.hookAwards.size) {
                                Row(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .height(50.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (poolLotteryAward.hookAwards[it].imageUri.isNullOrEmpty()) {
                                        Text(
                                            "?", color = Color.White,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                                .size(50.dp)
                                                .background(upColor)
                                                .wrapContentSize(Alignment.Center)
                                        )
                                    } else {
                                        AsyncImage(
                                            model = poolLotteryAward.hookAwards[it].imageUri,
                                            contentDescription = null,
                                            modifier = StyleCommon.FONT_MODIFIER
                                                .size(50.dp)
                                                .background(upColor)
                                        )
                                    }
                                    Divider(
                                        thickness = 50.dp, color = Color.Green,
                                        modifier = Modifier
                                            .background(if (poolLotteryAward.hookAwards[it].isUp) Color.Red else Color.Green)
                                            .fillMaxWidth(poolLotteryAward.hookAwards[it].count / 100f)
                                    )
                                    Text(
                                        text = poolLotteryAward.hookAwards[it].count.toString(),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = StyleCommon.FONT_MODIFIER
                                    )
//
                                }
                            }
                        }
                    }
                }
            }
        }
        items(lotteryAwardCountDto.userPoolLotteryAwards.size) {
            val userPoolLotteryAward = lotteryAwardCountDto.userPoolLotteryAwards[it]
            Column(
                modifier =
                Modifier
                    .wrapContentHeight()
                    .padding(20.dp)
                    .paint(
                        rememberAsyncImagePainter(userPoolLotteryAward.imageUri),
                        contentScale = ContentScale.FillBounds, alpha = 0.4f
                    )
                    .border(1.dp, Color.White)
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = userPoolLotteryAward.poolName.toString(),
                        color = color,
                        fontSize = 12.sp
                    )
                    Text(
                        text = userPoolLotteryAward.tag ?: "",
                        color = color,
                        fontSize = 18.sp
                    )
                }
                Row(
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "总抽卡", color = textColor)
                        Text(
                            text = userPoolLotteryAward.count.toString(),
                            color = color,
                            fontSize = 20.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "总出金", color = textColor)
                        Text(
                            text = userPoolLotteryAward.okCount.toString(),
                            color = color,
                            fontSize = 20.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "平均出金", color = textColor)
                        Text(
                            text = userPoolLotteryAward.avgCount.toString(),
                            color = color,
                            fontSize = 20.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "平均UP出金", color = textColor)
                        Text(
                            text = userPoolLotteryAward.avgUpCount.toString(),
                            color = color,
                            fontSize = 20.sp
                        )
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    modifier = Modifier.heightIn(60.dp, 180.dp),
                ) {
                    items(userPoolLotteryAward.hookAwards.size) { it ->
                        val h = userPoolLotteryAward.hookAwards[it]
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if (h.imageUri.isNullOrEmpty()) {
                                Text(
                                    "?", color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .background(upColor)
                                        .wrapContentSize(Alignment.Center)
                                )
                            } else {
                                AsyncImage(
                                    model = h.imageUri,
                                    contentDescription = null,
                                    modifier = StyleCommon.FONT_MODIFIER
                                        .size(50.dp)
                                        .background(upColor)
                                )
                            }
                            Text(
                                text = h.count.toString(), color = textColor,
                                modifier = StyleCommon.FONT_MODIFIER
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GetCookiesUri(
    gameName: String,
    modifier: Modifier = Modifier,
    toolsViewModel: ToolsViewModel,
    lotteryViewModel: LotteryViewModel,
    onBack: () -> Unit = {}
) {
    var uri by remember {
        mutableStateOf("")
    }
    val textColor = Color.White
    val api = toolsViewModel.getBaseAPI(gameName)
    Column(
        modifier
            .fillMaxSize()
            .paint(
                painterResource(id = api.bg_id),
                contentScale = ContentScale.Crop
            )
            .padding(20.dp),
    ) {
        IconButton(onClick = {
            onBack()
        }, modifier = Modifier.padding(10.dp)) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "返回",
                tint = Color.White
            )
        }
        Text(
            text = "- 抽卡分析", color = textColor, fontSize = 18.sp,
            modifier = Modifier.padding(10.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                value = uri,
                onValueChange = { uri = it },
                colors = OutlinedTextFieldDefaults.colors(textColor)
            )
            Row(
                Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = StyleCommon.ONE_SHAPE,
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.button_bg))
                ) {
                    Text(text = "如何获取", color = textColor)
                }
                Button(
                    onClick = {
                        // toolsViewModel.changeRecords(uri)
                        ThreadPoolManager.getInstance().addTask("init") {
                            lotteryViewModel.asyncMcRecord(gameName, uri)
                        }
                        onBack()
                    },
                    shape = StyleCommon.ONE_SHAPE,
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.button_bg))
                ) {
                    Text(text = "开始获取", color = textColor)
                }
            }
        }
    }
}


@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("MutableCollectionMutableState", "UnrememberedMutableState")
@Composable
fun MingChaoHome(
    mainController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    toolsViewModel: ToolsViewModel,
    gameProvider: () -> String,
    baseAPIProvider: () -> BaseAPI
) {
    val context = LocalContext.current
    val row = Modifier
        .size(100.dp)
    val api = baseAPIProvider()
    Column(modifier, verticalArrangement = Arrangement.Center) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            reverseLayout = false
        ) {
            item {
                Column(modifier = Modifier.clickable {
                    mainController.navigate("${PageRouteConfig.RANKING_HOME}/${gameProvider()}")
                }, horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = api.RAKING_ICON,
                        contentDescription = "排行榜",
                        modifier = StyleCommon.ICON_SIZE
                    )
                    Text(text = "排行榜")
                }
            }
            item {
                Column(modifier = Modifier.clickable {
                    mainController.navigate("${PageRouteConfig.TOOLS_MINGCHAO_LOTTERY_DETAIL}/${SystemApp.UserId}/${gameProvider()}")
                }, horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = api.SIMULATE_ICON,
                        contentDescription = "抽卡分析",
                        modifier = StyleCommon.ICON_SIZE
                    )
                    Text(text = "抽卡分析")
                }
            }
            item {
                Column(
                    modifier = row,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = api.LOTTERY_ICON,
                        contentDescription = "抽卡模拟",
                        modifier = StyleCommon.ICON_SIZE.clickable {
//                            mainController.navigate(MingChaoRoute.LOTTERY_ROUTE)
//                            lotteryViewModel.lotteryAwardCount()
                            val intent =
                                Intent(
                                    BaseContentProvider.context(),
                                    LotteryActivity::class.java
                                )
                            intent.putExtra("gameName", gameProvider())
                            context.startActivity(intent)
                        }
                    )
                    Text(text = "抽卡模拟")
                }
            }
            item {
                Column(
                    modifier = row,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = api.STREING_ICON,
                        contentDescription = "抽卡模拟",
                        modifier = StyleCommon.ICON_SIZE.clickable {
                            Utils.message(GlobalScope, "暂未开放", SystemApp.snackBarHostState)
                        }
                    )
                    Text(text = "练度统计")
                }
            }
            item {
                Column(
                    modifier = row,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = api.ROLE_ICON,
                        contentDescription = "角色强度榜",
                        modifier = StyleCommon.ICON_SIZE.clickable {
                            logger.debug { "角色强度榜: " + gameProvider() }
                            mainController.navigate("${PageRouteConfig.TOOLS_GAME_ROLE_RAKING}/${gameProvider()}")
                        }
                    )
                    Text(text = "角色强度榜")
                }
            }
        }

//        LazyVerticalGrid(
//            columns = GridCells.Fixed(4),
//            reverseLayout = false
//        ) {
//            item {
//                Column(
//                    modifier = row
//                        .clickable {
//                            toolsViewModel.catalogueId = 1105;
//                            toolsViewModel.catalogueName = "角色图鉴"
//                            mainController.navigate(MingChaoRoute.BOOK_LIST)
//                        },
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    AsyncImage(
//                        model = "https://prod-alicdn-community.kurobbs.com/forum/5e5bb6eaa1de43e6bcb66eb8d780e92c20240509.png",
//                        contentDescription = "角色图鉴",
//                        modifier = StyleCommon.ICON_SIZE
//                    )
//                    Text(text = "角色图鉴")
//                }
//            }
//            item {
//                Column(
//                    modifier = row.clickable {
//                        toolsViewModel.catalogueId = 1106;
//                        toolsViewModel.catalogueName = "武器图鉴"
//                        mainController.navigate(MingChaoRoute.BOOK_LIST)
//                    },
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    AsyncImage(
//                        model = "https://prod-alicdn-community.kurobbs.com/forum/f92b449640374599ae7326e2b46f40b620240509.png",
//                        contentDescription = "角色图鉴",
//                        modifier = StyleCommon.ICON_SIZE
//                    )
//                    Text(text = "武器图鉴")
//                }
//            }
//            item {
//                Column(
//                    modifier = row.clickable {
//                        toolsViewModel.catalogueId = 1107;
//                        toolsViewModel.catalogueName = "声骸图鉴"
//                        mainController.navigate(MingChaoRoute.BOOK_LIST)
//                    },
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    AsyncImage(
//                        model = "https://prod-alicdn-community.kurobbs.com/forum/6bcb87fced844da1a4e90989101751ab20240509.png",
//                        contentDescription = "声骸图鉴",
//                        modifier = StyleCommon.ICON_SIZE
//                    )
//                    Text(text = "声骸图鉴")
//                }
//            }
//            item {
//                Column(
//                    modifier = row.clickable {
//                        toolsViewModel.catalogueId = 1158;
//                        toolsViewModel.catalogueName = "敌人"
//                        mainController.navigate(MingChaoRoute.BOOK_LIST)
//                    },
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    AsyncImage(
//                        model = "https://prod-alicdn-community.kurobbs.com/forum/c530b90c692e491ab832ac475cd8784f20240509.png",
//                        contentDescription = "声骸图鉴",
//                        modifier = StyleCommon.ICON_SIZE
//                    )
//                    Text(text = "敌人")
//                }
//            }
//            item {
//                Column(
//                    modifier = row.clickable {
//                        toolsViewModel.catalogueId = 1218;
//                        toolsViewModel.catalogueName = "素材"
//                        mainController.navigate(MingChaoRoute.BOOK_LIST)
//                    },
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    AsyncImage(
//                        model = "https://prod-alicdn-community.kurobbs.com/forum/dd77cd02945040c2a86201649e5cf95c20240509.png",
//                        contentDescription = "素材",
//                        modifier = StyleCommon.ICON_SIZE
//                    )
//                    Text(text = "素材")
//                }
//            }
//            item {
//                Column(
//                    modifier = row.clickable {
//                        toolsViewModel.catalogueId = 1217;
//                        toolsViewModel.catalogueName = "补给"
//                        mainController.navigate(MingChaoRoute.BOOK_LIST)
//                    },
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    AsyncImage(
//                        model = "https://prod-alicdn-community.kurobbs.com/forum/661cd42d12a74cacafc35aa0ba53148720240509.png",
//                        contentDescription = "补给",
//                        modifier = Modifier.size(40.dp)
//                    )
//                    Text(text = "补给")
//                }
//            }
//        }


    }

}

/**
 * 图鉴列表
 * @param [toolsViewModel] 工具视图模型
 * @param [mainController] 主控制器
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Composable
fun HookList(
    toolsViewModel: ToolsViewModel,
    mainController: NavHostController
) {
    var roles by remember {
        mutableStateOf(listOf<RoleBook>())
    }
    ThreadPoolManager.getInstance().addTask("games", "role") {
        roles = toolsViewModel.getRoleBook(CatalogueDto(toolsViewModel.catalogueId))
    }
    Column(Modifier.fillMaxWidth()) {
        TopAppBar(title = {
            Text(
                text = toolsViewModel.catalogueName,
                color = Color.Black
            )
        }, navigationIcon = {
            IconButton(onClick = {
                mainController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "返回"
                )
            }
        })
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
        ) {
            items(roles.size) {

                val role = roles[it]
                HookItem(role.star, role.imageUri, role.imageUri)
            }
        }
    }
}

@Composable
fun HookItem(star: Int, imageUri: String, name: String) {
    Column(
        modifier = StyleCommon.HOOK_LIST,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    when (star) {
                        5 -> colorResource(R.color.star5)
                        4 -> colorResource(R.color.star4)
                        else -> colorResource(R.color.star0)
                    }
                )
        )
//        Row(
//            modifier = Modifier.height(20.dp),
//        ) {
//            if (star == 5) {
//                Image(
//                    bitmap = StyleCommon.startVitmap.asImageBitmap(),
//                    contentDescription = null,
//                    alignment = Alignment.CenterStart,
//                    modifier = Modifier.padding(start = 8.dp),
//                    contentScale = ContentScale.FillHeight
//                )
//            } else if (star == 4) {
//                Image(
//                    bitmap = StyleCommon.startVitmap.asImageBitmap(),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxWidth(0.8f),
//                    contentScale = ContentScale.FillHeight
//                )
//            } else if (star == 3) {
//                Image(
//                    bitmap = StyleCommon.startVitmap.asImageBitmap(),
//                    contentDescription = null,
//                    alignment = Alignment.CenterStart,
//                    modifier = Modifier.fillMaxWidth(0.5f),
//                    contentScale = ContentScale.FillHeight
//                )
//            }
//        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = name,
                color = Color.Black,
                fontSize = 10.sp
            )
        }
    }
}


@Composable
fun MCWIKI(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
}