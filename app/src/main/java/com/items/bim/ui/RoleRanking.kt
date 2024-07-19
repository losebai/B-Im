package com.items.bim.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.items.bim.common.consts.ConfigKey
import com.items.bim.common.ui.PagerList
import com.items.bim.common.ui.TopAppRow
import com.items.bim.dto.GameRoleDto
import com.items.bim.viewmodel.ConfigViewModel
import com.items.bim.viewmodel.ToolsViewModel
import java.util.stream.Collectors


@Composable
fun GameRoleList(list: List<GameRoleDto>) {
    LazyVerticalGrid(
        GridCells.Fixed(if (list.isEmpty()) 1 else 5),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 500.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(list.size) {
            HookItem(list[it].star, list[it].imageUri, list[it].name)
//            AsyncImage(model = list[it].imageUri, contentDescription = null)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameRoleRaking(
    gameName: String,
    toolsViewModel: ToolsViewModel,
    configViewModel: ConfigViewModel,
    navHostController: NavHostController
) {
    val imageUri: String? =
        configViewModel.getConfig(ConfigKey.GamesBG.format(gameName), String::class.java)
    Log.d("roleRanking", "GameRoleRaking ...")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                rememberAsyncImagePainter(model = imageUri),
                contentScale = ContentScale.Crop,
                alpha = 0.8f
            )
    ) {
        TopAppRow(navigationIcon = {
            navHostController.navigateUp()
        }, title = {
            Text(text = "角色强度排名")
        })
        val pools = toolsViewModel.appGameRole.appGameRoleRaking.keys.toList()
        PagerList(pools = pools, textColor = Color.White) { it ->
            Log.d("roleRanking", "PagerList ${it}...")
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = "最后更新时间${toolsViewModel.appGameRole.updateTime}" , color =Color.White)
                }
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    if (pools.isNotEmpty()){
                        val rakingMap = toolsViewModel.appGameRole.appGameRoleRaking[pools[it]]?.stream()
                            ?.collect(Collectors.groupingBy(GameRoleDto::raking))
                        if (rakingMap != null) {
                            val rakings = rakingMap.keys.toList().sorted()
                            items(rakings.size) { i ->
                                key(i) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "T${i}", color =Color.White, fontSize = 20.sp)
                                        rakingMap[rakings[i]]?.let { it1 -> GameRoleList(it1) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


