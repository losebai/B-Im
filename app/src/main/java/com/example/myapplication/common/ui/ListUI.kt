package com.example.myapplication.common.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.dto.LotteryPollEnum
import kotlinx.coroutines.launch

/**
 * 分页列表
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerList(pools: List<String>, textColor: Color,
              pageContent: @Composable PagerScope.(page: Int) -> Unit){
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { pools.size }
    LazyVerticalGrid(
        GridCells.Fixed(5),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(pools.size) {
            Column(
                modifier = Modifier
                    .width(50.dp)
                    .height(20.dp)
                    .clickable {
                        scope.launch {
                            pagerState.scrollToPage(it)
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = pools[it], color = textColor)
                Divider(
                    thickness = 4.dp,
                    color = if (pagerState.currentPage == it) Color.Green else Color.White,
                )
            }
        }
    }
    HorizontalPager(
        pagerState,
        modifier = Modifier.padding(top = 10.dp),
        pageContent=pageContent
    )
}