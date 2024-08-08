package com.items.bim.common.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.items.bim.R
import com.items.bim.entity.MessagesEntity


@Composable
fun MessageText(it : MessagesEntity){
    Surface(
        shape = RectangleShape,
        shadowElevation = 4.dp,
        tonalElevation = 1.dp,
        color = colorResource(id = R.color.sendMessage),
        modifier = Modifier
            .wrapContentWidth()
            .padding(1.dp)
    ) {
        Text(
            text = it.messageData,
            textAlign = TextAlign.Right,
            fontSize = 18.sp,
//            color = Color.White,
            modifier = Modifier.padding(4.dp),
//            overflow = TextOverflow.Ellipsis
        )
    }
}