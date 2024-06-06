package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.ui.UserInfoUI.EditPage
import com.example.myapplication.ui.UserInfoUI.UserInfoEdit


@Composable
@Preview(showBackground = true)
fun UserInfoTest(){
    val appUserEntity = UserEntity()
    appUserEntity.imageUrl = "https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1"
    val mainController = rememberNavController()
    UserInfoEdit(appUserEntity, mainController)
}


@Composable
@Preview(showBackground = true)
fun EditPageTest(){
    val mainController = rememberNavController()
    EditPage("123", mainController){

    }
}