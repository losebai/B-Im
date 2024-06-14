package com.example.myapplication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.GetCookiesUri
import com.example.myapplication.ui.HookList
import com.example.myapplication.viewmodel.ToolsViewModel


@Composable
@Preview
fun HookListTest(){
    val navHostController = rememberNavController()
    HookList(ToolsViewModel(),navHostController)
}

@Composable
@Preview
fun GetCookiesUriTest(){
    GetCookiesUri(Modifier.fillMaxSize(),
        ToolsViewModel())
}