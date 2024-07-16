package com.mozzartbet.greekkeno.core.network.model

import com.google.gson.annotations.SerializedName

internal data class DrawResponse(
    @SerializedName("gameId") val gameId: Int? = null,
    @SerializedName("drawId") val drawId: Int? = null,
    @SerializedName("drawTime") val drawTime: Long? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("drawBreak") val drawBreak: Double? = null,
    @SerializedName("visualDraw") val visualDraw: Double? = null,
    @SerializedName("pricePoints") val pricePoints: PricePointsResponse? = null,
    @SerializedName("winningNumbers") val winningNumbers: WinningNumbersResponse? = null,
    @SerializedName("prizeCategories") val prizeCategories: List<PrizeCategoriesResponse>? = null,
    @SerializedName("wagerStatistics") val wagerStatistics: WagerStatisticsResponse? = null
)
