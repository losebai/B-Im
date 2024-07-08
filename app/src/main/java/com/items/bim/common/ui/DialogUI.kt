package com.items.bim.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.io.File


@Composable
fun DialogImageAdd(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
) =
    Dialog(onDismissRequest = onDismissRequest, properties = properties) {
        var text by rememberSaveable { mutableStateOf("") }
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
        ) {
            Column() {
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(text = "创建文件夹")
                }
                Row() {
                    OutlinedTextField(value = text, onValueChange = { text = it }, label = {
                        Text(text = "名称")
                    })
                }
                Button(onClick = {
                    val file = File("./images/${text}")
                    if (!file.exists()) {
                        file.mkdir()
                    }
                }) {
                    Text(text = "确定")
                }
            }
        }
    }