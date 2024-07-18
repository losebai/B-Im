package com.items.bim.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.items.bim.R
import com.items.bim.common.consts.StyleCommon.rowModifier
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.ui.BButton
import com.items.bim.common.ui.HeadImage
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.common.util.Utils
import com.items.bim.config.PageRouteConfig
import com.items.bim.dto.AppDynamic
import com.items.bim.dto.CommunityEntity
import com.items.bim.entity.UserEntity
import com.items.bim.entity.toUserEntity
import com.items.bim.viewmodel.CommunityViewModel


/**
 * 动态消息
 */
@Composable
fun DynamicMessage(communityEntity: CommunityEntity, modifier: Modifier = Modifier) {
    var pingLun by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .border(1.dp, Color.Black)
            .padding(5.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        ) {
            HeadImage(
                onClick = {},
                userEntity = communityEntity.userEntity,
                modifier = Modifier.size(40.dp)
            )
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .height(50.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = communityEntity.userEntity.name, fontSize = 18.sp)
                Text(text = communityEntity.createTime, fontSize = 12.sp)
            }
        }
        Text(
            text = communityEntity.message, modifier = Modifier
                .padding(start = 10.dp), fontSize = 15.sp
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(
                when {
                    communityEntity.images.size == 1 -> 1
                    communityEntity.images.size == 2 -> 2
                    communityEntity.images.size >= 9 -> 3
                    else -> 3
                }
            ),
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            items(communityEntity.images.size) {
                AsyncImage(
                    communityEntity.images[it],
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(1.dp)
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
                    .padding(start = 5.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "点赞"
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 5.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "分享"
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            HeadImage(
                onClick = {},
                userEntity = communityEntity.userEntity,
                modifier = Modifier
                    .size(30.dp)
            )
            OutlinedTextField(value = pingLun, modifier = Modifier
                .padding(start = 5.dp)
                .width(250.dp)
                .height(30.dp),
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

@Composable
fun CommunityHome(
    userEntity: UserEntity = Utils.randomUser().toUserEntity(),
    background: String? = null,
    communityList: List<CommunityEntity>,
    modifier: Modifier = Modifier,
    mainController: NavHostController
) {
    val state = rememberLazyListState()
    Box(contentAlignment = Alignment.BottomEnd) {
        LazyColumn(state = state, modifier = modifier) {
            item {
                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier
                        .paint(
                            if (background == null) painterResource(id = R.drawable.test)
                            else rememberAsyncImagePainter(background)
                        )
                        .padding(10.dp)
                ) {
                    HeadImage(
                        onClick = {}, userEntity = userEntity,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
            items(communityList.size) {
                DynamicMessage(
                    communityList[it], modifier = Modifier
                        .heightIn(min = 300.dp, max = 500.dp)
                        .padding(1.dp)
                )
            }
        }
        FloatingActionButton(
            onClick = {
                mainController.navigate(PageRouteConfig.ADD_DYNAMIC)
            }, modifier = Modifier.offset(x = (-30).dp, y = (-70).dp),
            containerColor = colorResource(id = R.color.INIT)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Localized description")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDynamic(mainController: NavHostController,
               communityViewModel: CommunityViewModel
) {
    Column {
        Column {
            TopAppBar(
                title = {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = "动态")
                    }
                },
                navigationIcon = {
                    BButton(onClick = {
                        mainController.navigateUp()
                    }) {
                        Text(text = "取消")
                    }
                }, actions = {
                    BButton(onClick = {
                        ThreadPoolManager.getInstance().addTask("init", "add"){
                            communityViewModel.save()
                        }
                        mainController.navigateUp()
                    }) {
                        Text(text = "发表")
                    }
                }
            )
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .height(300.dp)
                    .padding(10.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomStart
            ) {
                OutlinedTextField(
                    value = communityViewModel.dynamicBody, onValueChange = {
                        communityViewModel.dynamicBody = it
                    }, modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                ){
                    items(communityViewModel.images.size){
                        AsyncImage(model = communityViewModel.images[it], contentDescription = null,
                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp),
                            contentScale=ContentScale.Crop)
                    }
                    item {
                        IconButton(
                            onClick = { mainController.navigate("${PageRouteConfig.IMAGE_SELECTOR}/addDynamic") },
                            modifier = Modifier.size(100.dp)
                        ) {
                            Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                        }
                    }
                }
            }
        }
        Row(
            rowModifier
                .background(Color.White)
                .padding(10.dp)
                .clickable {
                    mainController.navigate(PageRouteConfig.USER_INFO_NOTE)
                }, horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "权限设置", fontSize = 20.sp)
            Text(text = ">", modifier = Modifier.offset(0.dp, (-1).dp))
        }

    }
}

