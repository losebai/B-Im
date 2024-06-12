package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.HookList
import com.example.myapplication.viewmodel.ToolsViewModel


@Composable
@Preview
fun HookListTest(){
    val navHostController = rememberNavController()
    HookList(ToolsViewModel(),navHostController)
}