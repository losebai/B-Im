package com.items.bim.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun IconRowButton(imageVector: ImageVector, title: String, data : String = "" , onClick : () -> Unit = {}, buttonModifier: Modifier = Modifier){
    TextButton(
        onClick = onClick,
        modifier = buttonModifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = "Localized description"
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