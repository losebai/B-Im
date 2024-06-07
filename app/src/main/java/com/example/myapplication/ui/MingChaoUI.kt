package com.example.myapplication.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.config.PageRouteConfig
import com.example.myapplication.dto.LotteryCount
import com.example.myapplication.viewmodel.ToolsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LotterySimulate(
    lotteryMap: Map<Int, List<LotteryCount>>,
    mainController: NavHostController = rememberNavController()
) {
    val pagerState = rememberPagerState { 4 }
    val pool = arrayOf("角色", "武器", "常驻", "混合")
    val scope = rememberCoroutineScope()
    Column(Modifier.fillMaxWidth()) {
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


@SuppressLint("MutableCollectionMutableState")
@Composable
fun MingChaoHome(toolsViewModel: ToolsViewModel,
                 mainController: NavHostController = rememberNavController()) {
    val roles by remember {
        mutableStateOf(toolsViewModel.getRoleBook())
    }
    Column(verticalArrangement = Arrangement.Center) {
        Box {
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
                            contentDescription = "抽卡分析",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(text = "抽卡分析")
                    }
                }

                item {
                    Column(modifier = Modifier.size(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://prod-alicdn-community.kurobbs.com/forum/c530b90c692e491ab832ac475cd8784f20240509.png",
                            contentDescription = "抽卡模拟",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(text = "抽卡模拟")
                    }
                }
                item {
                    Column(modifier = Modifier.size(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://prod-alicdn-community.kurobbs.com/forum/5e5bb6eaa1de43e6bcb66eb8d780e92c20240509.png",
                            contentDescription = "角色图鉴",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(text = "角色图鉴")
                    }
                }
                item {
                    Column(modifier = Modifier.size(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://prod-alicdn-community.kurobbs.com/forum/f92b449640374599ae7326e2b46f40b620240509.png",
                            contentDescription = "角色图鉴",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(text = "武器图鉴")
                    }
                }
                item {
                    Column(modifier = Modifier.size(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://prod-alicdn-community.kurobbs.com/forum/f92b449640374599ae7326e2b46f40b620240509.png",
                            contentDescription = "角色图鉴",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(text = "武器图鉴")
                    }
                }
                item {
                    Column(modifier = Modifier.size(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://prod-alicdn-community.kurobbs.com/forum/6bcb87fced844da1a4e90989101751ab20240509.png",
                            contentDescription = "声骸图鉴",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(text = "声骸图鉴")
                    }
                }
                item {
                    Column(modifier = Modifier.size(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://prod-alicdn-community.kurobbs.com/forum/c530b90c692e491ab832ac475cd8784f20240509.png",
                            contentDescription = "声骸图鉴",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(text = "资源")
                    }
                }
                item {
                    Column(modifier = Modifier.size(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://prod-alicdn-community.kurobbs.com/forum/dd77cd02945040c2a86201649e5cf95c20240509.png",
                            contentDescription = "声骸图鉴",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(text = "素材")
                    }
                }
                item {
                    Column(modifier = Modifier.size(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://prod-alicdn-community.kurobbs.com/forum/661cd42d12a74cacafc35aa0ba53148720240509.png",
                            contentDescription = "声骸图鉴",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(text = "补给")
                    }
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
        ){
            items(roles.size){
                Column(modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally){
                    AsyncImage(model = roles[it].imageUri,
                        contentDescription = null, modifier = Modifier.background(if (roles[it].star == 5)
                            colorResource(R.color.star5) else  colorResource(id = R.color.purple_500)))
                    Text(text = roles[it].name)
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.background(
                            colorResource(R.color.bg_black))) {
                        Text(text = roles[it].name,
                            modifier = Modifier.background(Color.White))
                    }
                }
            }
        }
    }

}