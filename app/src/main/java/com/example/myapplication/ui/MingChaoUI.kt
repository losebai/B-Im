package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.activity.LotteryActivity
import com.example.myapplication.common.consts.AppAPI
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.provider.BaseContentProvider
import com.example.myapplication.common.ui.TopAppBarBack
import com.example.myapplication.common.util.ImageUtils
import com.example.myapplication.common.util.ThreadPoolManager
import com.example.myapplication.config.MingChaoRoute
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.dto.LotteryCount
import com.example.myapplication.dto.RoleBook
import com.example.myapplication.dto.mingchao.CatalogueDto
import com.example.myapplication.viewmodel.ToolsViewModel
import kotlinx.coroutines.launch

/**
 * 抽卡模拟
 * @param [lotteryMap] 彩票地图
 * @param [mainController] 主控制器
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LotterySimulate(
    lotteryMap: Map<Int, List<LotteryCount>>,
    mainController: NavHostController = rememberNavController()
) {
    val pagerState = rememberPagerState { 4 }
    val pool = arrayOf("角色", "武器", "常驻", "混合")
    val scope = rememberCoroutineScope()
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        TopAppBar(title = { /*TODO*/ }, navigationIcon = {
            IconButton(onClick = {
                mainController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "返回"
                )
            }
        })
        Column {
            Text(text = "UID:12323213")
            Text(text = "欧皇度为")
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "超级无敌欧皇",
                    fontSize = 25.sp,
                    modifier = Modifier.background(colorResource(id = R.color.golden))
                )
            }
            Row() {
                Text(text = "小保底不歪概率: ")
                Text(text = "1%")
            }
            Row() {
                Text(text = "总次数")
                Text(text = "10000")
            }
        }
        Column {
            LazyVerticalGrid(GridCells.Fixed(4), modifier = Modifier.fillMaxWidth()) {
                items(pool.size) {
                    Column(
                        modifier = Modifier
                            .width(50.dp)
                            .clickable {
                                scope.launch {
                                    pagerState.scrollToPage(it)
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = pool[it])
                        Divider(
                            thickness = 2.dp,
                            color = if (pagerState.currentPage == it) Color.Green else Color.Transparent,
                        )
                    }
                }
            }
            HorizontalPager(pagerState) {
                LazyColumn {
                    lotteryMap[it]?.let { lotteryCountList ->
                        items(lotteryCountList.size) {
                            Row(modifier = Modifier.padding(2.dp)) {
                                AsyncImage(
                                    model = lotteryCountList[it].roleImageUri,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(Color(0xFFFF9800))
                                        .padding(2.dp)
                                )
                                Divider(
                                    thickness = 30.dp, color = Color.Green,
                                    modifier = Modifier
                                        .background(if (lotteryCountList[it].isOk) Color.Red else Color.Green)
                                        .fillMaxWidth(lotteryCountList[it].count / 100f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetCookiesUri(modifier: Modifier = Modifier, toolsViewModel: ToolsViewModel,
                  mainController: NavHostController = rememberNavController()){
    var uri by remember {
        mutableStateOf("")
    }
    Column(
        modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "如何获取")
        Row {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.mipmap.ic_web_refresh),
                    contentDescription = null,
                    tint = colorResource(R.color.theme)
                )
            }
            Text(text = "同步云端")
        }
        TopAppBar(title = { /*TODO*/ }, navigationIcon = {
            IconButton(onClick = {
                mainController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "返回"
                )
            }
        })
        OutlinedTextField(value = uri, onValueChange = {uri = it} )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "开始获取")
        }
        Text(text = "正在获取xx,第几页")
    }
}


@SuppressLint("MutableCollectionMutableState", "UnrememberedMutableState")
@Composable
fun MingChaoHome(
    toolsViewModel: ToolsViewModel,
    mainController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val row = Modifier
        .size(100.dp)
    Column(modifier,verticalArrangement = Arrangement.Center) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            reverseLayout = false
        ) {
            item {
                Column(modifier = Modifier.clickable {
                    mainController.navigate(PageRouteConfig.TOOLS_MINGCHAO_LOTTERY_DETAIL)
                }, horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = "https://prod-alicdn-community.kurobbs.com/forum/c530b90c692e491ab832ac475cd8784f20240509.png",
                        contentDescription = "排行榜",
                        modifier = StyleCommon.ICON_SIZE
                    )
                    Text(text = "排行榜")
                }
            }
            item {
                Column(modifier = Modifier.clickable {
                    mainController.navigate(PageRouteConfig.TOOLS_MINGCHAO_LOTTERY_DETAIL)
                }, horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = "https://prod-alicdn-community.kurobbs.com/forum/c530b90c692e491ab832ac475cd8784f20240509.png",
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
                        model = "https://prod-alicdn-community.kurobbs.com/forum/c530b90c692e491ab832ac475cd8784f20240509.png",
                        contentDescription = "抽卡模拟",
                        modifier = StyleCommon.ICON_SIZE.clickable {
//                            mainController.navigate(MingChaoRoute.LOTTERY_ROUTE)
                            val intent = Intent(BaseContentProvider.context(), LotteryActivity::class.java)
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
                        model = "https://prod-alicdn-community.kurobbs.com/forum/c530b90c692e491ab832ac475cd8784f20240509.png",
                        contentDescription = "抽卡模拟",
                        modifier = StyleCommon.ICON_SIZE
                    )
                    Text(text = "练度统计")
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            reverseLayout = false
        ){
        item {
                Column(
                    modifier = row
                        .clickable {
                            toolsViewModel.catalogueId = 1105;
                            toolsViewModel.catalogueName = "角色图鉴"
                            mainController.navigate(MingChaoRoute.BOOK_LIST)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = "https://prod-alicdn-community.kurobbs.com/forum/5e5bb6eaa1de43e6bcb66eb8d780e92c20240509.png",
                        contentDescription = "角色图鉴",
                        modifier = StyleCommon.ICON_SIZE
                    )
                    Text(text = "角色图鉴")
                }
            }
            item {
                Column(
                    modifier = row.clickable {
                        toolsViewModel.catalogueId = 1106;
                        toolsViewModel.catalogueName = "武器图鉴"
                        mainController.navigate(MingChaoRoute.BOOK_LIST)
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = "https://prod-alicdn-community.kurobbs.com/forum/f92b449640374599ae7326e2b46f40b620240509.png",
                        contentDescription = "角色图鉴",
                        modifier = StyleCommon.ICON_SIZE
                    )
                    Text(text = "武器图鉴")
                }
            }
            item {
                Column(
                    modifier = row.clickable {
                        toolsViewModel.catalogueId = 1107;
                        toolsViewModel.catalogueName = "声骸图鉴"
                        mainController.navigate(MingChaoRoute.BOOK_LIST)
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = "https://prod-alicdn-community.kurobbs.com/forum/6bcb87fced844da1a4e90989101751ab20240509.png",
                        contentDescription = "声骸图鉴",
                        modifier = StyleCommon.ICON_SIZE
                    )
                    Text(text = "声骸图鉴")
                }
            }
            item {
                Column(
                    modifier = row.clickable {
                        toolsViewModel.catalogueId = 1158;
                        toolsViewModel.catalogueName = "敌人"
                        mainController.navigate(MingChaoRoute.BOOK_LIST)
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = "https://prod-alicdn-community.kurobbs.com/forum/c530b90c692e491ab832ac475cd8784f20240509.png",
                        contentDescription = "声骸图鉴",
                        modifier = StyleCommon.ICON_SIZE
                    )
                    Text(text = "敌人")
                }
            }
            item {
                Column(
                    modifier = row.clickable {
                        toolsViewModel.catalogueId = 1218;
                        toolsViewModel.catalogueName = "素材"
                        mainController.navigate(MingChaoRoute.BOOK_LIST)
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = "https://prod-alicdn-community.kurobbs.com/forum/dd77cd02945040c2a86201649e5cf95c20240509.png",
                        contentDescription = "素材",
                        modifier = StyleCommon.ICON_SIZE
                    )
                    Text(text = "素材")
                }
            }
            item {
                Column(
                    modifier = row.clickable {
                        toolsViewModel.catalogueId = 1217;
                        toolsViewModel.catalogueName = "补给"
                        mainController.navigate(MingChaoRoute.BOOK_LIST)
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = "https://prod-alicdn-community.kurobbs.com/forum/661cd42d12a74cacafc35aa0ba53148720240509.png",
                        contentDescription = "补给",
                        modifier = Modifier.size(40.dp)
                    )
                    Text(text = "补给")
                }
            }
        }


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
                Column(
                    modifier = StyleCommon.HOOK_LIST,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = roles[it].imageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                when (roles[it].star) {
                                    5 -> colorResource(R.color.star5)
                                    4 -> colorResource(R.color.star4)
                                    else -> colorResource(R.color.star0)
                                }
                            )
                    )
                    Row(
                        modifier = Modifier.height(20.dp),
                    ) {
                        if (roles[it].star == 5) {
                            Image(
                                bitmap = StyleCommon.startVitmap.asImageBitmap(),
                                contentDescription = null,
                                alignment = Alignment.CenterStart,
                                modifier = Modifier.padding(start = 8.dp),
                                contentScale = ContentScale.FillHeight
                            )
                        } else if (roles[it].star == 4) {
                            Image(
                                bitmap = StyleCommon.startVitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                contentScale = ContentScale.FillHeight
                            )
                        } else if (roles[it].star == 3) {
                            Image(
                                bitmap = StyleCommon.startVitmap.asImageBitmap(),
                                contentDescription = null,
                                alignment = Alignment.CenterStart,
                                modifier = Modifier.fillMaxWidth(0.5f),
                                contentScale = ContentScale.FillHeight
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = roles[it].name,
                            color = Color.White
                        )
                    }
                }
            }
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