package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.ui.SettingHome


@Composable
@Preview
fun SettingHomeTest(){
    SettingHome(userEntity=UserEntity(1,"test", "https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1",
        "这是一个签名" ))
}