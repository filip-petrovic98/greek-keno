package com.mozzartbet.greekkeno.core.network.model

import com.google.gson.annotations.SerializedName

internal data class SideBetsResponse(
    @SerializedName("evenNumbersCount") val evenNumbersCount: Int? = null,
    @SerializedName("oddNumbersCount") val oddNumbersCount: Int? = null,
    @SerializedName("winningColumn") val winningColumn: Int? = null,
    @SerializedName("winningParity") val winningParity: String? = null,
    @SerializedName("oddNumbers") val oddNumbers: List<Int>? = null,
    @SerializedName("evenNumbers") val evenNumbers: List<Int>? = null
)
