package com.mozzartbet.greekkeno.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mozzartbet.greekkeno.feature.game.drawdetails.navigation.drawDetailsScreen
import com.mozzartbet.greekkeno.feature.game.drawdetails.navigation.navigateToDrawDetailsScreen

@Composable
fun GreekKenoNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = MAIN_ROUTE
    ) {
        mainScreen(navController::navigateToDrawDetailsScreen)
        drawDetailsScreen(navController::popBackStack)
    }
}
