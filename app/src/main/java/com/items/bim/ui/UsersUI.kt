package com.items.bim.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.items.bim.R
import com.items.bim.common.consts.StyleCommon
import com.items.bim.common.consts.UserStatus
import com.items.bim.common.ui.ExpandableItem
import com.items.bim.common.ui.HeadImage
import com.items.bim.common.util.Utils
import com.items.bim.entity.UserEntity
import com.items.bim.viewmodel.UserViewModel

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
        LazyColumn(modifier.padding(10.dp)) {
            items(userViewModel.users.value.size) {
                val userGroup = userViewModel.users.value[it]
                ExpandableItem(userGroup.groupName,
                    modifier=Modifier.heightIn(min = 50.dp, 999.dp),
                    subItemStartPadding = 0,
                    expandable = it == 0
                   ){
                    LazyColumn(modifier=Modifier.heightIn(min = 50.dp, 999.dp)) {
                        items(userGroup.users.size){it2 ->
                            val user = userGroup.users[it2]
                            Button(
                                onClick = {
                                    onClick(user)
                                },
                                shape = StyleCommon.ZERO_SHAPE,
                                colors = ButtonDefaults.buttonColors(Color.White)
                            ) {
                                Row {
                                    HeadImage(userEntity = user, modifier = StyleCommon.HEAD_IMAGE) {
                                    }
                                    Column(modifier = Modifier.padding(start = 10.dp)) {
                                        Text(
                                            text = user.name, modifier = Modifier
                                                .fillMaxWidth(),
                                            color = Color.Black, fontSize = StyleCommon.NAME_FONT_SIZE
                                        )
                                        Row {
                                            Text(text = "[${user.status.tag}] ", fontSize = 12.sp,
                                                color = if (user.status.value == UserStatus.ON_LINE.value) colorResource(
                                                    R.color.ON_LINE
                                                ) else Color.Black)
                                            Text(
                                                text = Utils.stringLen(user.note),
                                                fontSize = 12.sp,
                                                color = Color.Black
                                            )
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

