package com.items.bim.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
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
    val pagerState = rememberPagerState { if (pools.isEmpty()) 1 else pools.size }
    LogCompositions(msg = "PagerList")
    Column(modifier) {
        LazyVerticalGrid(
            GridCells.Fixed(if (pools.isEmpty()) 1 else pools.size),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
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
            pageContent = { pageContent(it) }
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
    val scope = rememberCoroutineScope()
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState { if (pools.isEmpty()) 1 else pools.size }
        TopAppBar(title = {
            LazyVerticalGrid(
                GridCells.Fixed(4),
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
                        Text(text = pools[it], color = textColor, fontSize = 15.sp)
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
            pageContent = {
                pageContent(it)
            }
        )
    }
}

/**
 * 可展开的列表
 *
 * @param title 列表标题
 * @param modifier Modifier
 * @param endText 列表标题的尾部文字，默认为空
 * @param subItemStartPadding 子项距离 start 的 padding 值
 * @param subItem 子项
 * */
@Composable
fun ExpandableItem(
    title: String,
    modifier: Modifier = Modifier,
    endText: String = "",
    subItemStartPadding: Int = 8,
    expandable: Boolean = false,
    subItem: @Composable () -> Unit
) {
    var isShowSubItem by remember { mutableStateOf(expandable) }
    val arrowRotateDegrees: Float by animateFloatAsState(if (isShowSubItem) 90f else 0f, label = "")
    Column(modifier = modifier) {
        Row {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clickable {
                        isShowSubItem = !isShowSubItem
                    }
            ) {
                Icon(
                    Icons.Outlined.KeyboardArrowRight,
                    contentDescription = title,
                    modifier = Modifier.rotate(arrowRotateDegrees)
                )
                Text(text = title)
            }
            Row {
                if (endText.isNotBlank()) {
                    Text(
                        text = endText,
                        modifier = modifier
                            .padding(end = 4.dp)
                            .widthIn(0.dp, 100.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        AnimatedVisibility(visible = isShowSubItem) {
            Column(modifier = Modifier.padding(start = subItemStartPadding.dp)) {
                subItem()
            }
        }
    }
}

