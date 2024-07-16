package com.mozzartbet.greekkeno.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.mozzartbet.greekkeno.navigation.GreekKenoNavHost

@Composable
fun GreekKenoApp(navController: NavHostController) {
    GreekKenoNavHost(navController = navController)
}
