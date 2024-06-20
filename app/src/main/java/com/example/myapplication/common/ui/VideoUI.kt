package com.example.myapplication.common.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.example.myapplication.common.provider.PlayerProvider


@Composable
fun VideScreen (uri : String){
    val playerProvider: PlayerProvider = PlayerProvider.getInstance()
    val player = playerProvider.build()
    playerProvider.play(uri)
    PlayerSurface(modifier = Modifier.width(400.dp).height(400.dp)){
        it.player = player
    }
}

@Composable
fun PlayerSurface(
    modifier: Modifier,
    onPlayerViewAvailable: (PlayerView) -> Unit = {}
) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                useController = true
                onPlayerViewAvailable(this)
            }
        },
        modifier = modifier
    )
}
