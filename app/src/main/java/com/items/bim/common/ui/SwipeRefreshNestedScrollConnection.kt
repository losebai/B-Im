package com.items.bim.common.ui

import android.util.Log
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.roundToInt

private const val DragMultiplier = 0.5f

private class SwipeRefreshNestedScrollConnection(
    private val state: MySwipeRefreshState,
    private val coroutineScope: CoroutineScope,
    private val onRefresh: () -> Unit,
    private val onLoadMore: () -> Unit
) : NestedScrollConnection {
    var refreshEnabled: Boolean = false//是否开启下拉刷新
    var loadMoreEnabled: Boolean = false//是否开启上拉加载
    var refreshTrigger: Float = 100f//最大的下上拉的距离
    var indicatorHeight: Float = 50f//顶部、底部下上组合项的高度

    private var isTop = false //是否是顶部的下拉
    private var isBottom = false//是否是达到

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset = when {
        //刷新和更多都禁用 则不处理
        !refreshEnabled && !loadMoreEnabled -> Offset.Zero
        //当处于刷新状态或者更多状态，不处理
        state.loadState != NORMAL -> Offset.Zero
        source == NestedScrollSource.Drag -> {
//            Log.v("hj", "onPreScroll available = $available")
            if (available.y > 0 && isBottom) {
                onScroll(available)
            } else if (available.y < 0 && isTop) {
                onScroll(available)
            } else {
                Offset.Zero
            }
        }
        else -> Offset.Zero
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        //刷新和更多都禁用 则不处理
        if (!refreshEnabled && !loadMoreEnabled) {
            return Offset.Zero
        }
        //当处于刷新状态或者更多状态，不处理
        else if (state.loadState != NORMAL) {
            return Offset.Zero
        } else if (source == NestedScrollSource.Drag) {
//            Log.d("hj", "onPostScroll available = $available , consumed = $consumed")
            if (available.y < 0) {
                if (!isBottom) {
                    isBottom = true
                }
                if (isBottom) {
                    return onScroll(available)
                }

            } else if (available.y > 0) {
                if (!isTop) {
                    isTop = true
                }
                if (isTop) {
                    return onScroll(available)
                }
            }
        }
        return Offset.Zero
    }

    private fun onScroll(available: Offset): Offset {
        if (!isBottom && !isTop) {
            return Offset.Zero
        }
        if (available.y > 0 && isTop) {
            state.isSwipeInProgress = true
        } else if (available.y < 0 && isBottom) {
            state.isSwipeInProgress = true
        } else if (state.indicatorOffset.roundToInt() == 0) {
            state.isSwipeInProgress = false
        }

        val newOffset = (available.y * DragMultiplier + state.indicatorOffset).let {
            if (isTop) it.coerceAtLeast(0f) else it.coerceAtMost(0f)
        }
        val dragConsumed = newOffset - state.indicatorOffset

        return if (dragConsumed.absoluteValue >= 0.5f) {
            coroutineScope.launch {
                state.dispatchScrollDelta(
                    dragConsumed,
                    if (isTop) TOP else BOTTOM,
                    refreshTrigger,
                )
            }
            // Return the consumed Y
            Offset(x = 0f, y = dragConsumed / DragMultiplier)
        } else {
            Offset.Zero
        }
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        // If we're dragging, not currently refreshing and scrolled
        // past the trigger point, refresh!
        if (state.loadState == NORMAL && abs(state.indicatorOffset) >= indicatorHeight) {
            if (isTop) {
                onRefresh()
            } else if (isBottom) {
                onLoadMore()
            }
        }

        // Reset the drag in progress state
        state.isSwipeInProgress = false

        // Don't consume any velocity, to allow the scrolling layout to fling
        return Velocity.Zero
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        return Velocity.Zero.also {
            isTop = false
            isBottom = false
        }
    }
}



@Composable
fun MySwipeRefresh(
    state: MySwipeRefreshState,
    onRefresh: () -> Unit,//下拉刷新回调
    onLoadMore: () -> Unit,//上拉加载更多回调
    modifier: Modifier = Modifier,
    refreshTriggerDistance: Dp = 120.dp,//indication可见的最大高度
    indicationHeight: Dp = 56.dp,//indication的高度
    refreshEnabled: Boolean = true,//是否支持下拉刷新
    loadMoreEnabled: Boolean = true,//是否支持上拉加载更多
    indicator: @Composable BoxScope.(modifier: Modifier, state: MySwipeRefreshState, indicatorHeight: Dp) -> Unit = { m, s, height ->
        LoadingIndicator(m, s, height)
    },//顶部或者底部的Indicator
    content: @Composable (modifier: Modifier) -> Unit,
) {
    val refreshTriggerPx = with(LocalDensity.current) { refreshTriggerDistance.toPx() }
    val indicationHeightPx = with(LocalDensity.current) { indicationHeight.toPx() }

    // Our LaunchedEffect, which animates the indicator to its resting position
    LaunchedEffect(state.isSwipeInProgress) {
        if (!state.isSwipeInProgress) {
            // If there's not a swipe in progress, rest the indicator at 0f
            state.animateOffsetTo(0f)
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val updatedOnRefresh = rememberUpdatedState(onRefresh)
    val updatedOnLoadMore = rememberUpdatedState(onLoadMore)

    val nestedScrollConnection = remember(state, coroutineScope) {
        SwipeRefreshNestedScrollConnection(
            state,
            coroutineScope,
            onRefresh = { updatedOnRefresh.value.invoke() },
            onLoadMore = { updatedOnLoadMore.value.invoke() }
        )
    }.apply {
        this.refreshEnabled = refreshEnabled
        this.loadMoreEnabled = loadMoreEnabled
        this.refreshTrigger = refreshTriggerPx
        this.indicatorHeight = indicationHeightPx
    }

    BoxWithConstraints(modifier.nestedScroll(connection = nestedScrollConnection)) {
        if (!state.isSwipeInProgress)
            LaunchedEffect((state.loadState == REFRESHING || state.loadState == LOADING_MORE)) {
                //回弹动画
                animate(
                    animationSpec = tween(durationMillis = 300),
                    initialValue = state.progress.offset,
                    targetValue = when (state.loadState) {
                        LOADING_MORE -> indicationHeightPx
                        REFRESHING -> indicationHeightPx
                        else -> 0f
                    }
                ) { value, _ ->
                    if (!state.isSwipeInProgress) {
                        state.progress = state.progress.copy(
                            offset = value,
                            fraction = min(1f, value / refreshTriggerPx)
                        )
                    }
                }
            }

        val offsetDp = with(LocalDensity.current) {
            state.progress.offset.toDp()
        }
        //子可组合项 根据state.progress来设置子可组合项的padding
        content(
           when (state.progress.location) {
                TOP -> Modifier.padding(top = offsetDp)
                BOTTOM -> Modifier.padding(bottom = offsetDp)
                else -> Modifier
            }
        )
        if (state.progress.location != NONE) {
            //顶部、底部的indicator 纵坐标跟随state.progress移动
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(refreshTriggerDistance)
                .graphicsLayer {
                    translationY =
                        if (state.progress.location == LOADING_MORE) constraints.maxHeight - state.progress.offset
                        else state.progress.offset - refreshTriggerPx
                }
            ) {
                indicator(
                    Modifier.align(if (state.progress.location == TOP) Alignment.BottomStart else Alignment.TopStart),
                    state,
                    indicationHeight
                )
            }
        }
    }
}

