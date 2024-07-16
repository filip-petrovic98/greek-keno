package com.mozzartbet.greekkeno.feature.game.drawdetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mozzartbet.greekkeno.feature.game.drawdetails.ui.DrawDetailsScreen

const val DRAW_DETAILS = "draw_details"
const val DRAW_DETAILS_ROUTE = "$DRAW_DETAILS/{drawId}"

fun NavController.navigateToDrawDetailsScreen(drawId: Int) {
    navigate("$DRAW_DETAILS/$drawId")
}

fun NavGraphBuilder.drawDetailsScreen(popBackStack: () -> Unit) {
    composable(DRAW_DETAILS_ROUTE) {
        DrawDetailsScreen {
            popBackStack()
        }
    }
}
