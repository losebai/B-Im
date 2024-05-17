package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.ui.HeadImage
import com.example.myapplication.common.util.Utils
import com.example.myapplication.entity.CommunityEntity
import com.example.myapplication.entity.UserEntity


/**
 * 动态消息
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DynamicMessage(communityEntity: CommunityEntity, modifier: Modifier = Modifier) {
    var pingLun by remember {
        mutableStateOf("")
    }
    var messageSend by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
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
                text = communityEntity.message, modifier = Modifier
                    .padding(start = 10.dp), fontSize = 24.sp
            )
            LazyVerticalGrid(
//                columns = GridCells.FixedSize(
//                    when {
//                        communityEntity.images.size == 1 -> 240.dp
//                        communityEntity.images.size == 2 -> 190.dp
//                        communityEntity.images.size in 3..8 -> 120.dp
//                        communityEntity.images.size >= 9 -> 90.dp
//                        else -> 100.dp
//                    }
//                ),
                columns = GridCells.Fixed(
                    when {
                        communityEntity.images.size == 1 -> 1
                        communityEntity.images.size == 2 -> 2
                        communityEntity.images.size >= 9 -> 3
                        else -> 3
                    }
                ),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                items(communityEntity.images.size) {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = StyleCommon.ZERO_SHAPE,
                        modifier = Modifier.padding(1.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(communityEntity.images[it]),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(100.dp)
                        )
                    }
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommunityHome(
    userEntity: UserEntity = Utils.randomUser(),
    background: String? = null,
    communityList: List<CommunityEntity>,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    val state = rememberLazyListState()
    LazyColumn(state = state, modifier = modifier) {
        item {
            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier
                    .paint(
                        if (background == null) painterResource(id = R.drawable.test)
                        else rememberAsyncImagePainter(background)
                    )
            ) {
                HeadImage(
                    onClick = {}, userEntity = userEntity,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(15.dp)
                )
            }
        }
        items(communityList) {
            DynamicMessage(
                it, modifier = Modifier
                    .height(
                        when (it.images.size) {
                            1 -> 100.dp
                            2 -> 400.dp
                            else -> 500.dp
                        }
                    )
                    .padding(top = 10.dp)
            )
        }
    }
}

