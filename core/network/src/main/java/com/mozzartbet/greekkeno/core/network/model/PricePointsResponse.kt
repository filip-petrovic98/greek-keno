package com.mozzartbet.greekkeno.core.network.model

import com.google.gson.annotations.SerializedName

internal data class PricePointsResponse(
    @SerializedName("addOn") val addOn: List<AddOnResponse>? = null,
    @SerializedName("amount") val amount: Double? = null
)
