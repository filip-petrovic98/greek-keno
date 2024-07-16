package com.mozzartbet.greekkeno.core.model.draws

data class SideBetsModel(
    val evenNumbersCount: Int,
    val oddNumbersCount: Int,
    val winningColumn: Int,
    val winningParity: String,
    val oddNumbers: List<Int>,
    val evenNumbers: List<Int>
)
