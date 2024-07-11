package com.items.bim.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController



var groupName by mutableIntStateOf(0)

@Composable
fun GetTopAppBar() {
}

@Composable
fun GetBottomBar() {
}

@Composable
fun Context(
    content: @Composable (PaddingValues) -> Unit,
    topBar: @Composable () -> Unit = { GetTopAppBar() },
    bottomBar: @Composable () -> Unit = { GetBottomBar() },
    floatingActionButton: @Composable () -> Unit = { },
) {
    Scaffold(
        snackbarHost = {
        },
        topBar = {
            topBar()
        },
        bottomBar = {
            bottomBar()
        },
        floatingActionButton = { floatingActionButton() },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        content = content
    )
}
