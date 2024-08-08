package com.items.bim.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.items.bim.R
import com.items.bim.common.ui.ImageButton
import com.items.bim.config.PageRouteConfig


@Preview(showBackground = true)
@Composable
fun CommonTools(mainController: NavHostController= rememberNavController()){
    Column(modifier = Modifier.fillMaxSize()) {
        Row(){
            ImageButton("图片识别",
                painter = painterResource(id = R.drawable.camerd),
                fontSize = 18.sp,
                modifier = Modifier.padding(10.dp)
            ){
            }
            ImageButton("图库中心",
                painter = painterResource(id = R.drawable.th),
                fontSize = 18.sp,
                modifier = Modifier.padding(10.dp)){
                mainController.navigate(PageRouteConfig.IMAGE_GROUP_LIST)
            }
            ImageButton("二维码识别",
                painter = painterResource(id = R.drawable.er_wei_ma),
                fontSize = 18.sp,
                modifier = Modifier.padding(10.dp)){
            }
        }
    }
}