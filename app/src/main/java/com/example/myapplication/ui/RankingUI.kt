package com.example.myapplication.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.myapplication.common.ui.HeadImage
import com.example.myapplication.common.ui.PagerList
import com.example.myapplication.dto.UserGameDto
import com.example.myapplication.mc.consts.LotteryPollEnum


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RankingHome(user: UserGameDto) {
    val pools by remember {
        mutableStateOf(LotteryPollEnum.entries.map { it.poolName }.toList())
    }
    Column {
        Card {
            Row {
                HeadImage(path = user.imageUri) {
                }
                Column {
                    Text(text = "UID:${user.uid}")
                    Text(text = "称号:${user.tag}")
                }
                Text(text = user.zonRaking.toString())
            }
        }
        PagerList(pools, Color.White){

        }
    }
}
