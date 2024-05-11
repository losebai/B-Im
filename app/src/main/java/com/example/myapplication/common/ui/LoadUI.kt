//package com.example.myapplication.common.ui
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.LinearProgressIndicator
//import androidx.compose.material3.ListItem
//import androidx.compose.material3.Text
//import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.input.nestedscroll.nestedScroll
//
//@Composable
//fun test(){
//
//    var itemCount by remember { mutableStateOf(15) }
//    val state = rememberPullToRefreshState()
//    if (state.isRefreshing) {
//        LaunchedEffect(true) {
//            // fetch something
//            delay(1500)
//            itemCount += 5
//            state.endRefresh()
//        }
//    }
//    Box(Modifier.nestedScroll(state.nestedScrollConnection)) {
//        LazyColumn(Modifier.fillMaxSize()) {
//            if (!state.isRefreshing) {
//                items(itemCount) {
//                    ListItem({ Text(text = "Item ${itemCount - it}") })
//                }
//            }
//        }
//        PullToRefreshContainer(state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
//    }
//}
//
//
