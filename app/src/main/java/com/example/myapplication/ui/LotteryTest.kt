package com.example.myapplication.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.viewmodel.LotteryViewModel


@Composable
fun MCRoleLotteryHome(lotteryViewModel: LotteryViewModel,onDispatch: ()-> Unit = {}) {
    var poolIndex by remember {
        mutableIntStateOf(0)
    }
    Box(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
        ,
    ){
        AsyncImage(
            lotteryViewModel.pools[poolIndex].poolBg,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale =  ContentScale.FillBounds
        )
        Row(modifier = Modifier.padding(20.dp)) {
            IconButton(onClick =onDispatch) {
                Icon(Icons.Filled.ArrowBack , contentDescription = null)
            }
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                items(lotteryViewModel.pools.size){
                    AsyncImage(
                        model = lotteryViewModel.pools[it].poolImageUri,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            poolIndex = it
                        }
                    )
                }
            }
            Divider(
                color = Color.White,
                thickness = 5.dp,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Column(modifier = Modifier
                .padding(start = 10.dp, top = 40.dp, bottom = 40.dp)
                .fillMaxHeight(),
                verticalArrangement=Arrangement.SpaceBetween) {
                Column {
                    Text(text = "角色活动唤取", color= Color.Yellow, fontSize = 10.sp)
                    Text(text = "角色活动唤取", color= Color.White, fontSize = 20.sp)
                }
                LazyColumn {
                    item {
                        Text(text = "以下四星概率提升", color= Color.White, fontSize = 10.sp)
                    }
                    items(lotteryViewModel.pools[poolIndex].array.size){
                        AsyncImage(
                            model = lotteryViewModel.pools[poolIndex].array[it],
                            contentDescription = null,
                            modifier = StyleCommon.ICON_SIZE
                        )
                    }
                }
            }
            Row(modifier = Modifier) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "唤取一次")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "唤取十次")
                }
            }
        }
    }
}
