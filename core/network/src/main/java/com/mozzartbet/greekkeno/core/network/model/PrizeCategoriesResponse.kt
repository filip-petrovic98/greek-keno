package com.mozzartbet.greekkeno.core.network.model

import com.google.gson.annotations.SerializedName

internal data class PrizeCategoriesResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("divident") val divident: Double? = null,
    @SerializedName("winners") val winners: Double? = null,
    @SerializedName("distributed") val distributed: Double? = null,
    @SerializedName("jackpot") val jackpot: Double? = null,
    @SerializedName("fixed") val fixed: Double? = null,
    @SerializedName("categoryType") val categoryType: Double? = null,
    @SerializedName("gameType") val gameType: String? = null
)
