package com.items.bim.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.items.bim.common.ui.PagerList
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.dto.AppGameRole
import com.items.bim.dto.GameRoleDto
import com.items.bim.viewmodel.ToolsViewModel
import java.util.stream.Collector
import java.util.stream.Collectors


@Composable
fun GameRoleList(list: List<GameRoleDto>){
    LazyRow {
        items(list){
            AsyncImage(model = it.imageUri, contentDescription = null)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameRoleRaking(gameName: String ,toolsViewModel: ToolsViewModel,
                   navHostController: NavHostController){
    var appGameRole by remember {
        mutableStateOf(AppGameRole())
    }
    ThreadPoolManager.getInstance().addTask("init", "GameRoleRaking"){
        appGameRole = toolsViewModel.getAppGameRole(gameName)
    }
    val pools = appGameRole.appGameRoleRaking.keys.toList()
    Column {
        IconButton(onClick = {
            navHostController.navigateUp()
        }, modifier = Modifier.padding(top = 30.dp, start = 10.dp)) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "返回",
                tint = Color.White
            )
        }
        PagerList(pools = pools, textColor = Color.White) {it ->
            LazyColumn {

                item{
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End) {
                        Text(text = "最后更新时间${appGameRole.updateTime}")
                    }
                }
                val rakingMap = appGameRole.appGameRoleRaking[pools[it]]?.stream()?.collect(Collectors.groupingBy(GameRoleDto::raking))
                if (rakingMap != null){
                    items(rakingMap.keys.toList()) {i ->
                        key(i) {
                            rakingMap[i]?.let { it1 -> GameRoleList(it1) }
                        }
                    }
                }
            }
        }
    }
}


