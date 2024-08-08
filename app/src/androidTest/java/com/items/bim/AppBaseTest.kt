package com.items.bim

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun InputButtom(modifier: Modifier= Modifier, onSend : (String)->Unit = {}){
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
                        onSend(sendData)
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