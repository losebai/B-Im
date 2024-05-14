package com.example.myapplication.common.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.Drag
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity

private class PullRefreshNestedScrollConnection(
    private val onPull: (pullDelta: Float) -> Float,
    private val onRelease: (pullDelta: Float) -> Float,
    private val enabled: Boolean,
) : NestedScrollConnection {

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset = when {
        !enabled -> Offset.Zero
        // 向上滑动，父布局先处理（收回偏移），走 onPull 回调，并根据处理结果返回被消费掉的 Offset
        source == Drag && available.y < 0 -> Offset(0f, onPull(available.y)) // Swiping up
        else -> Offset.Zero
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset = when {
        !enabled -> Offset.Zero
        // 向下滑动，如果子布局处理完了还有剩余（拉到顶了还往下拉），就展示偏移
        source == Drag && available.y > 0 -> Offset(0f, onRelease(available.y)) // Pulling down
        else -> Offset.Zero
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        onRelease(available.y)
        return Velocity.Zero
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.pullRefresh(
    onRelease: (pullDelta: Float) -> Float,
    onPull: (pullDelta: Float) -> Float,
    enabled: Boolean = true
) = nestedScroll(PullRefreshNestedScrollConnection(onPull, onRelease, enabled))
