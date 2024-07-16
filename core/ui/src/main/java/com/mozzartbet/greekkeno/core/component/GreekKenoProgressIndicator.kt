package com.mozzartbet.greekkeno.core.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GreekKenoProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier, color = MaterialTheme.colorScheme.secondary)
}
