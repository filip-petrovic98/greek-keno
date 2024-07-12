package com.mozzartbet.greekkeno.core.ui

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val Sunglow = Color(0xFFFFCB37)
internal val Jacarta = Color(0xFF2F256C)
internal val Tamarillo = Color(0xFFA31111)

internal val DarkColorScheme =
    darkColorScheme(
        primary = Jacarta,
        secondary = Sunglow,
        tertiary = Tamarillo,
        surface = Jacarta
    )

internal val LightColorScheme =
    lightColorScheme(
        primary = Jacarta,
        secondary = Sunglow,
        tertiary = Tamarillo,
        surface = Jacarta
        /* Other default colors to override
        background = Color(0xFFFFFBFE),
        surface = Color(0xFFFFFBFE),
        onPrimary = Color.White,
        onSecondary = Color.White,
        onTertiary = Color.White,
        onBackground = Color(0xFF1C1B1F),
        onSurface = Color(0xFF1C1B1F),
         */
    )
