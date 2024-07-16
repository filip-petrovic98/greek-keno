package com.mozzartbet.greekkeno.core.model.draws

data class PrizeCategoriesModel(
    val id: Int,
    val divident: Double,
    val winners: Double,
    val distributed: Double,
    val jackpot: Double,
    val fixed: Double,
    val categoryType: Double,
    val gameType: String
)
