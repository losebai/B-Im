package com.items.bim.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp


@Composable
fun IconRowButton(imageVector: Painter, title: String, data : String = "", onClick : () -> Unit = {}, modifier: Modifier = Modifier){
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector,
                contentDescription = null,
                modifier=Modifier.size(20.dp)
            )
            Row {
                Text(
                    text = title,
                )
                Text(
                    text = data,
                )
            }
        }
    }
}

@Composable
fun IconRowButton(imageVector: ImageVector, title: String, data : String = "", onClick : () -> Unit = {}, modifier: Modifier = Modifier)
 =  IconRowButton(imageVector = rememberVectorPainter(imageVector), title = title, data = data, onClick = onClick, modifier = modifier)