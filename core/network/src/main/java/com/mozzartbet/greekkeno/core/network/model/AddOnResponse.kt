package com.mozzartbet.greekkeno.core.network.model

import com.google.gson.annotations.SerializedName

internal data class AddOnResponse(
    @SerializedName("amount") val amount: Double? = null,
    @SerializedName("gameType") val gameType: String? = null
)
