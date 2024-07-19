package com.items.bim.viewmodel

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.items.bim.config.MenuRouteConfig

class HomeViewModel: ViewModel() {

    var page by mutableStateOf(MenuRouteConfig.ROUTE_USERS)

    var settingDrawerState by mutableStateOf(DrawerState(DrawerValue.Closed))

    var darkTheme by mutableStateOf(false)


}