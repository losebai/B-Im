package com.example.myapplication.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.ui.HeadImage
import com.example.myapplication.common.ui.MySwipeRefresh
import com.example.myapplication.common.ui.MySwipeRefreshState
import com.example.myapplication.common.ui.NORMAL
import com.example.myapplication.entity.MessagesDetail
import com.example.myapplication.entity.MessagesEntity


@Composable
fun MessagesList(messages: List<MessagesDetail>, modifier: Modifier){
    val state = MySwipeRefreshState(NORMAL)
    MySwipeRefresh(state = state, onRefresh = { /*TODO*/ }, onLoadMore = { /*TODO*/ },
        modifier=modifier) {
        LazyColumn(it) {
            items(messages){
                Row {
                    HeadImage(it.sendUserImageUri,modifier = StyleCommon.HEAD_IMAGE) {
                    }
                    Row {
                        Text(text = it.sendUserName, fontSize = 18.sp)
                        Text(text = it.messageData.substring(0, if (it.messageData.length < 20) it.messageData.length else 20))
                    }
                }
            }
        }
    }
}