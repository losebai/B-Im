package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.ui.MessagesDetail
import com.example.myapplication.viewmodel.MessagesViewModel


@Composable
@Preview(showBackground = true)
fun MessagesDetailTest(){
    val appUserEntity = UserEntity()
    appUserEntity.imageUrl = "https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1"
    val messagesViewModel: MessagesViewModel = MessagesViewModel(LocalContext.current)
    val navHostController = rememberNavController()
    Modifier.background(Color.White)
    MessagesDetail(appUserEntity, messagesViewModel, navHostController)
}