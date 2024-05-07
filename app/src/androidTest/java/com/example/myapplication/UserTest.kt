package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.entity.ImageEntity
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.ui.UserList
import java.util.ArrayList

@Preview(showBackground = true)
@Composable
fun UserListTest(){
    val list: ArrayList<UserEntity> = ArrayList()
    list.add(UserEntity(1,"test", "https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1",
        "这是一个签名" ))
    list.add(UserEntity(1,"test", "https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1",
        "这是一个签名" ))
    list.add(UserEntity(1,"test", "https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1",
        "这是一个签名" ))
    list.add(UserEntity(1,"test", "https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1",
        "这是一个签名" ))
    list.add(UserEntity(1,"test", "https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1",
        "这是一个签名" ))
    UserList(list){}
}