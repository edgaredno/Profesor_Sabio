package com.example.profesorsabio.ui.video

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.profesorsabio.R

@Composable
fun VideoScreen(onVideoEnd: () -> Unit) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val videoUri = Uri.parse("android.resource://${context.packageName}/${R.raw.videoapp}")
            val mediaItem = MediaItem.fromUri(videoUri)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        val listener = object : androidx.media3.common.Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == androidx.media3.common.Player.STATE_ENDED) {
                    onVideoEnd()
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}