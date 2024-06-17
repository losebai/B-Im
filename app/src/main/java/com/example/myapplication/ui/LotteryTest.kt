package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.common.consts.StyleCommon


@Composable
@Preview
fun MCRoleLotteryHome(onDispatch: ()-> Unit = {}) {
    Box(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
        ,
    ){
        Image(
            painter = painterResource(id = R.drawable.mc_yinlin),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale =  ContentScale.FillBounds
        )
        Row(modifier = Modifier.padding(20.dp)) {
            IconButton(onClick =onDispatch) {
                Icon(Icons.Filled.ArrowBack , contentDescription = null)
            }
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.mc_yinlin),
                        contentDescription = null,
                        modifier = StyleCommon.ICON_SIZE
                    )
                }
                item {
                    Image(
                        painter = painterResource(id = R.drawable.mc_yinlin),
                        contentDescription = null,
                        modifier = StyleCommon.ICON_SIZE
                    )
                }
                item {
                    Image(
                        painter = painterResource(id = R.drawable.mc_yinlin),
                        contentDescription = null,
                        modifier = StyleCommon.ICON_SIZE
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
                Column {
                    Text(text = "以下四星概率提升", color= Color.White, fontSize = 10.sp)
//                    Text(text = "角色活动唤取", color= Color.White, fontSize = 20.sp)
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
