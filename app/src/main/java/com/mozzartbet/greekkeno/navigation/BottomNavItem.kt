package com.mozzartbet.greekkeno.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.outlined.ListAlt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.mozzartbet.greekkeno.R

enum class BottomNavItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val labelRes: Int
) {
    UPCOMING_DRAWS(
        selectedIcon = Icons.AutoMirrored.Filled.ListAlt,
        unselectedIcon = Icons.AutoMirrored.Filled.List,
        labelRes = R.string.upcoming_draws
    ),
    LIVE_DRAW(
        selectedIcon = Icons.Default.PlayCircle,
        unselectedIcon = Icons.Default.PlayCircleOutline,
        labelRes = R.string.live_draw
    ),
    DRAW_RESULTS(
        selectedIcon = Icons.Default.History,
        unselectedIcon = Icons.Outlined.History,
        labelRes = R.string.draw_results
    )
}
