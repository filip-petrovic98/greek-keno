package com.mozzartbet.greekkeno.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mozzartbet.greekkeno.ui.MainScreen

const val MAIN_ROUTE = "main"

fun NavGraphBuilder.mainScreen(openDrawDetails: (drawId: Int) -> Unit) {
    composable(MAIN_ROUTE) {
        MainScreen(openDrawDetails)
    }
}
