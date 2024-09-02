package com.items.bim.common.consts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.items.bim.common.util.ImageUtils

object StyleCommon {

    val ZERO_PADDING = PaddingValues(
        start = 0.dp,
        top = 0.dp,
        end = 0.dp,
        bottom = 0.dp
    )

    val IMAGE_BUTTON_SHAPE = RoundedCornerShape(20)

    val ZERO_SHAPE = RoundedCornerShape(0)

    val ONE_SHAPE = RoundedCornerShape(1)

    val FOUR_SHAPE = RoundedCornerShape(5)


    val HEAD_IMAGE = Modifier.size(40.dp)

    val IMAGE_SIZE =  Modifier.height(100.dp).width(100.dp);

    val HOOK_LIST = Modifier.padding(5.dp)

    val ICON_SIZE = Modifier.size(40.dp)


    val FONT_MODIFIER = Modifier.wrapContentSize(Alignment.Center)

    val NAME_FONT_SIZE = 14.sp

    val rowModifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)

    val inputModifier = Modifier.fillMaxWidth().height(60.dp)
}


