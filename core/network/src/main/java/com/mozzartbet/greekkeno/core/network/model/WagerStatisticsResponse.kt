package com.mozzartbet.greekkeno.core.network.model

import com.google.gson.annotations.SerializedName

internal data class WagerStatisticsResponse(
    @SerializedName("columns") val columns: Double? = null,
    @SerializedName("wagers") val wagers: Double? = null,
    @SerializedName("addOn") val addOn: List<String>? = null
)
