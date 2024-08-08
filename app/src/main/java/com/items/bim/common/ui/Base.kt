package com.items.bim.common.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.items.bim.common.consts.StyleCommon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarBack(modifier: Modifier=Modifier,
                  title: @Composable () -> Unit,
                  mainController: NavHostController,
                  content: @Composable () -> Unit){
    Column(
        modifier
    ) {
        TopAppBar(title = title, navigationIcon = {
            IconButton(onClick = {
                mainController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "返回"
                )
            }
        })
        content()
    }
}

class Ref(var value: Int)

@Composable
inline fun LogCompositions(msg: String) {
    val ref = remember { Ref(0) }
    SideEffect { ref.value++ }
//    Text(text = "$msg 重组次数 ${ref.value}")
    Log.d("RecompositionLog", "Compositions: $msg ${ref.value}")
}

@Composable
fun TopAppRow(navigationIcon : () -> Unit , title: @Composable () -> Unit){
    Row(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(0.2f)) {
            IconButton(onClick = navigationIcon, modifier = Modifier.padding(top = 30.dp, start = 10.dp)) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "返回",
                    tint = Color.White
                )
            }
        }
        title()
    }
}

@Composable
fun WrapperUI(content: @Composable () -> Unit){
    content()
}



@Composable
fun InputBottom(modifier: Modifier= Modifier, onSendData : (String)->Unit = {}, onCloseSendData: ()->Unit = {}){
    var sendData by remember {
        mutableStateOf("")
    }
    BottomAppBar(modifier = modifier.background(Color.Gray)) {
        Row(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = sendData,
                modifier= Modifier.background(Color.White),
                onValueChange = {
                    sendData = it
                }, colors = TextFieldDefaults.colors(Color.White),
            )
            if (sendData.isEmpty()) {
                IconButton(
                    onClick = {
                        onCloseSendData()
                    }, modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Localized description"
                    )
                }
            } else {
                IconButton(onClick = {
                    // 关闭键盘
//                            activity.dismissKeyboardShortcutsHelper()
                    onSendData(sendData)
                    if (sendData.isNotEmpty()) {
                        sendData = ""
                    }
                },modifier = Modifier.size(50.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    }
}