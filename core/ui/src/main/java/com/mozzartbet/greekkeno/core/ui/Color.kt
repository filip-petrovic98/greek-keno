package com.mozzartbet.greekkeno.core.ui

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val Bittersweet = Color(0xFFFF6262)
internal val Sunglow = Color(0xFFFFCB37)
internal val Jacarta = Color(0xFF2F256C)
internal val Mirage = Color(0xFF18152B)
internal val BlueHaze = Color(0xFFC0BCD5)
internal val Martinique = Color(0xFF3B375E)
internal val GovernorBay = Color(0xFF4232AA)
internal val Tamarillo = Color(0xFFA31111)
internal val Mercury = Color(0xFFE7E7E7)
internal val Silver = Color(0xFFC5C5C5)

internal val DarkColorScheme =
    darkColorScheme(
        primary = Jacarta,
        secondary = Sunglow,
        tertiary = Tamarillo,
        surface = Jacarta,
        surfaceVariant = Martinique,
        background = Mirage,
        onBackground = Mercury,
        onSurface = Mercury,
        onSurfaceVariant = Silver,
        onPrimary = Mercury,
        error = Bittersweet
    )

internal val LightColorScheme =
    lightColorScheme(
        primary = Jacarta,
        secondary = Sunglow,
        tertiary = Tamarillo,
        surface = Jacarta,
        surfaceVariant = GovernorBay,
        background = BlueHaze,
        onBackground = Color.Black,
        onSurface = Mercury,
        onSurfaceVariant = Silver,
        onPrimary = Mercury,
        error = Bittersweet
    )
