package com.example.myapplication.common.consts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object StyleCommon {

    val ZERO_PADDING = PaddingValues(
        start = 0.dp,
        top = 0.dp,
        end = 0.dp,
        bottom = 0.dp
    )


    val ROUND_SHAPE = RoundedCornerShape(50)

    val ZERO_SHAPE = RoundedCornerShape(0)

    val ONE_SHAPE = RoundedCornerShape(1)


    val HEAD_IMAGE = Modifier.height(50.dp).width(50.dp)
}


