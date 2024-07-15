package com.items.bim.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.items.bim.common.consts.StyleCommon
import com.items.bim.common.ui.HeadImage
import com.items.bim.common.ui.LoadingIndicator
import com.items.bim.common.ui.MySwipeRefresh
import com.items.bim.common.ui.MySwipeRefreshState
import com.items.bim.common.ui.NORMAL
import com.items.bim.common.ui.REFRESHING
import com.items.bim.common.util.ThreadPoolManager
import com.items.bim.common.util.Utils
import com.items.bim.entity.UserEntity
import com.items.bim.entity.AppUserEntity
import com.items.bim.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import io.github.oshai.kotlinlogging.KotlinLogging


private val logger = KotlinLogging.logger {}

@Composable
fun SearchUser(
    username: String, modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit, title: String = "搜索"
) =
    OutlinedTextField(
        value = username,
        placeholder = { Text(text = title, fontSize = 12.sp) },
        prefix = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Localized description",
                modifier = Modifier
                    .padding(1.dp)
                    .size(20.dp)
            )
        },
        onValueChange = onValueChange,
        modifier = modifier
    )



@SuppressLint("UnrememberedMutableState")
@Composable
fun UserList(
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier,
    onClick: (UserEntity) -> Unit
) {
    val list by remember {
        mutableStateOf(userViewModel.users)
    }
//    val user = AppUserEntity()
//    MySwipeRefresh(
//        state = state,
//        indicator = { _modifier, s, indicatorHeight ->
//            LoadingIndicator(_modifier, s, indicatorHeight)
//        },
//        onRefresh = {
//            scope.launch {
//                state.loadState = REFRESHING
//                ThreadPoolManager.getInstance().addTask("user", "UserList"){
//                    list = userViewModel.getReferUser(user)
//                }
//                logger.info { "用户下拉刷新" }
//                state.loadState = NORMAL
//            }
//        },
//        onLoadMore = {
//        },
//        modifier = modifier
//    ) {
        LazyColumn(modifier) {
            items(list.size) {
                Button(
                    onClick = {
                        onClick(list[it])
                    },
                    shape = StyleCommon.ZERO_SHAPE,
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Row {
                        HeadImage(userEntity = list[it], modifier = StyleCommon.HEAD_IMAGE) {
                        }
                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Text(
                                text = list[it].name, modifier = Modifier
                                    .fillMaxWidth(),
                                color = Color.Black, fontSize = StyleCommon.NAME_FONT_SIZE
                            )
                            Row {
                                Text(text = "[${list[it].status.tag}] ", fontSize = 12.sp,)
                                Text(
                                    text = Utils.stringLen(list[it].note),
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
//    }

}

