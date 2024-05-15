package com.example.myapplication.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

@Composable
fun BoxScope.LoadingIndicatorSample(
    modifier: Modifier,
    state: MySwipeRefreshState,
    indicatorHeight: Dp
) {
    val height = max(30.dp, with(LocalDensity.current) {
        state.progress.offset.toDp()
    })
    Box(
        modifier
            .fillMaxWidth()
            .height(height), contentAlignment = Alignment.Center
    ) {
        if (state.isSwipeInProgress) {
            if (state.progress.offset <= with(LocalDensity.current) { indicatorHeight.toPx() }) {
                Text(text = if (state.progress.location == TOP) "下拉刷新" else "上拉加载更多")
            } else {
                Text(text = if (state.progress.location == TOP) "松开刷新" else "松开加载更多")
            }
        } else {
            AnimatedVisibility(state.loadState == REFRESHING || state.loadState == LOADING_MORE) {
                //加载中
                CircularProgressIndicator()
            }
        }
    }
}
