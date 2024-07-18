package com.items.bim.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.items.bim.R


@Composable
fun BButton(modifier: Modifier = Modifier, onClick: () -> Unit,content: @Composable RowScope.() -> Unit) = Button(
    modifier= modifier.buttonClick(onClick),
    onClick = onClick,
    shape = RoundedCornerShape(10),
    colors = ButtonDefaults.buttonColors(
        containerColor = colorResource(R.color.active_button),
        contentColor = Color.Black
    ),
    elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp),
    contentPadding = PaddingValues(1.dp),
    content=content
)

@Composable
fun BIConButton() = IconButton(onClick = { /*TODO*/ }) {
    
}

fun Modifier.buttonClick(onClick: () -> Unit) = composed {
    this.clickable( onClick = onClick,
            // 去除点击效果
            indication = null,
            interactionSource = remember {
                MutableInteractionSource()
            })
}

@Composable
fun AppBarButton(active: Boolean,
                 imageVector: ImageVector,
                 activeColor : Color,
                 text: String,
                 modifier: Modifier,
                 onClick: () -> Unit){
    Column(modifier = modifier.buttonClick {
        onClick()
    }, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Localized description",
            tint = if (active) activeColor else Color.Black
        )
        Text(
            text = text,
            fontSize = 12.sp,
            color = if (active) activeColor else Color.Black
        )
    }
}