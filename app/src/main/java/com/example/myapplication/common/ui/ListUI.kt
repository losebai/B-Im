package com.example.myapplication.common.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.launch

private val logger = KotlinLogging.logger {
}

/**
 * 分页列表
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerList(
    modifier: Modifier = Modifier,
    pools: List<String>, textColor: Color,
    pageContent: @Composable PagerScope.(page: Int) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { pools.size }
    Column(modifier) {
        LazyVerticalGrid(
            GridCells.Fixed(5),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(pools.size) {
                Column(
                    modifier = Modifier
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
                        color = if (pagerState.currentPage == it) Color.Green else Color.Transparent,
                    )
                }
            }
        }
        HorizontalPager(
            pagerState,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxHeight(),
            pageContent = pageContent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TopPagerList(
    modifier: Modifier = Modifier,
    pools: List<String>, textColor: Color,
    pageContent: @Composable PagerScope.(page: Int) -> Unit,
) {
    logger.info { "TopPagerList" }
    val scope = rememberCoroutineScope()
    Column(modifier.fillMaxWidth(),
        horizontalAlignment=Alignment.CenterHorizontally) {
        val pagerState = rememberPagerState { pools.size }
        TopAppBar(title = {
            LazyVerticalGrid(
                GridCells.Fixed(5),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(pools.size) {
                    Column(
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    pagerState.scrollToPage(it)
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = pools[it], color = textColor, fontSize = 18.sp)
                        Divider(
                            thickness = 4.dp,
                            color = if (pagerState.currentPage == it) Color.Green else Color.Transparent,
                        )
                    }
                }
            }
        })
        HorizontalPager(
            pagerState,
            pageContent = pageContent
        )
    }
}