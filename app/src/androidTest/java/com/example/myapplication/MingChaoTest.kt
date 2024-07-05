package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.GetCookiesUri
import com.example.myapplication.ui.HookList
import com.example.myapplication.viewmodel.LotteryViewModel
import com.example.myapplication.viewmodel.ToolsViewModel


@Composable
//@Preview
fun HookListTest(){
    val navHostController = rememberNavController()
    HookList(ToolsViewModel(),navHostController)
}

@Composable
//@Preview
fun GetCookiesUriTest(){
    GetCookiesUri("鸣潮",Modifier.fillMaxSize(),
        ToolsViewModel(),
        LotteryViewModel()
    )
}

@Composable
@Preview
fun IconTest(){
    Button(onClick = { /*TODO*/ },) {
        Text(text = "唤取一次")
    }
    Button(onClick = { /*TODO*/ }) {
        Image(painter = painterResource(id = R.drawable.mc_icons),
            contentDescription = null, modifier = Modifier.size(40.dp)
                .clip(
                CutCornerShape(20,10, 20,40)
            ))
        Text(text = "唤取十次")
    }
}