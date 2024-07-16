package com.mozzartbet.greekkeno.feature.game.livedraw.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.mozzartbet.greekkeno.feature.game.livedraw.ui.CONSTANTS.LIVE_DRAW_URL

private object CONSTANTS {
    const val LIVE_DRAW_URL = "https://www.mozzartbet.com/sr/lotto-animation/26#/"
}

@Composable
fun LiveDrawScreen(modifier: Modifier = Modifier) {
    LiveDrawContent(modifier)
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun LiveDrawContent(modifier: Modifier) {
    AndroidView(
        modifier = modifier,
        factory = {
            WebView(it).apply {
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                this.settings.javaScriptEnabled = true
                this.settings.domStorageEnabled = true
            }
        },
        update = {
            it.loadUrl(LIVE_DRAW_URL)
        }
    )
}
