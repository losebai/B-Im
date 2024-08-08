package com.items.bim

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.items.bim.entity.UserEntity
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
    UserList(list)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SearchUserTest(){
//    SearchUser("", onValueChange = {
//    }, modifier = Modifier.fillMaxWidth())
    TopAppBar(title = {
        OutlinedTextField(value = "",
            placeholder  = { Text(text = "搜索") },
            prefix = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Localized description",
                    modifier=Modifier.padding(0.dp)
                )
            },
            onValueChange = {  },
            modifier= Modifier
                .fillMaxWidth()

        )
    }, modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp))
}

@Composable
fun UserList(list : List<UserEntity>){}