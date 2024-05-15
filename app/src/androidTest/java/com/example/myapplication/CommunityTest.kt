package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.common.util.Utils
import com.example.myapplication.entity.CommunityEntity
import com.example.myapplication.ui.CommunityHome
import com.example.myapplication.ui.DynamicMessage
import java.util.ArrayList


@Composable
@Preview(showBackground = true)
fun DynamicMessageTest(){
    val images = ArrayList<String>()
    images.add("https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1")
    images.add("https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1")
    images.add("https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1")
    val communityEntity = CommunityEntity(Utils.randomUser(),
        "",
        images,
        "2024-05-01 00:00:00"
        )
    DynamicMessage(communityEntity)
}

@Composable
@Preview(showBackground = true)
fun CommunityHomeTest(){
    val communityList = ArrayList<CommunityEntity>()
    CommunityHome(communityList=communityList, background = "")
}