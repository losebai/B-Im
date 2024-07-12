package com.items.bim

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.items.bim.common.util.Utils
import com.items.bim.dto.CommunityEntity
import com.items.bim.entity.toUserEntity
import com.items.bim.ui.CommunityHome
import com.items.bim.ui.DynamicMessage
import java.util.ArrayList


@Composable
@Preview(showBackground = true)
fun DynamicMessageTest(){
    val images = ArrayList<String>()
    images.add("https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1")
    images.add("https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1")
    images.add("https://profile-avatar.csdnimg.cn/fbf610cacb2842c1aeb9582d3f0ef4f4_weixin_45904404.jpg!1")
    val communityEntity = CommunityEntity(Utils.randomUser().toUserEntity(),"",
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