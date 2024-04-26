package com.example.myapplication.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

abstract class UIBase {

    lateinit var navHostController: NavHostController;

    var groupName by mutableStateOf(0)


    abstract fun GetTopAppBar()


    abstract fun GetBottomBar()


    abstract fun Context()
}