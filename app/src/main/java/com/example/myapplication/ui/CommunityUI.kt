package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.ui.HeadImage
import com.example.myapplication.common.ui.MySwipeRefreshState
import com.example.myapplication.common.util.Utils
import com.example.myapplication.entity.CommunityEntity
import com.example.myapplication.entity.UserEntity


/**
 * 动态消息
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DynamicMessage(communityEntity: CommunityEntity,modifier: Modifier= Modifier) {
    Card(modifier=modifier.background(Color.White)) {
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
                Text(text = communityEntity.userEntity.name, fontSize = 16.sp)
                Text(text = communityEntity.createTime, fontSize = 14.sp)
            }
        }
        Text(
            text = communityEntity.message, modifier = Modifier
                .padding(start = 10.dp), fontSize = 24.sp
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 96.dp),
            modifier = Modifier
                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(communityEntity.images.size) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = StyleCommon.ZERO_SHAPE,
                    modifier = Modifier.padding(1.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
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
    Column( modifier = modifier) {
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
        LazyColumn {
            items(communityList) {
                DynamicMessage(it, modifier = Modifier.height(200.dp))
            }
        }
    }
}

