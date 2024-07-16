package com.mozzartbet.greekkeno.core.model.draws

import java.util.Date

data class DrawModel(
    val gameId: Int,
    val drawId: Int,
    val drawTime: Date?,
    val status: String,
    val drawBreak: Double,
    val visualDraw: Double,
    val pricePoints: PricePointsModel?,
    val winningNumbers: WinningNumbersModel,
    val prizeCategories: List<PrizeCategoriesModel>,
    val wagerStatistics: WagerStatisticsModel?
)
