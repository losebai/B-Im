package com.items.bim.common.provider

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class PlayerProvider {

    private var player: ExoPlayer? = null

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    private var lister: Player.Listener? = null

    var onClose: () -> Unit = {}

    companion object {
        fun getInstance() = SingleHolder.SINGLE_HOLDER
    }

    object SingleHolder {
        val SINGLE_HOLDER = PlayerProvider()
    }

    private fun addListener(){
        lister = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    // Active playback.
                } else {
                    if (player?.playbackState == Player.STATE_ENDED){
                        onClose()
                    }
                }
            }
        }
        lister?.let {
            player?.addListener(it)
        }
    }

    fun play(videoUri: String){
        this.addListener()
        val mediaItem = MediaItem.fromUri(videoUri)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
    }

    fun build(): ExoPlayer {
        return player ?: ExoPlayer.Builder(BaseContentProvider.context()).build().also {
            player = it
        }
    }
    fun release() {
        player?.run {
            playbackPosition = this.currentPosition
            playWhenReady = this.playWhenReady
            //如果注册了监听，等等
            lister?.let {
                removeListener(it)
            }
            release()
        }
    }
}