package com.mozzartbet.greekkeno.core.network.model

import com.google.gson.annotations.SerializedName

internal data class WinningNumbersResponse(
    @SerializedName("list") val list: List<Int>? = null,
    @SerializedName("bonus") val bonus: List<Int>? = null,
    @SerializedName("sideBets") val sideBets: SideBetsResponse? = null
)
