package com.mozzartbet.greekkeno.core.model.draws

data class WinningNumbersModel(
    val list: List<Int>,
    val bonus: List<Int>,
    val sideBets: SideBetsModel?
)
