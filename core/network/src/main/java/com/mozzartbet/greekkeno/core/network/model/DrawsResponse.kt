package com.mozzartbet.greekkeno.core.network.model

import com.google.gson.annotations.SerializedName

internal data class DrawsResponse(
    @SerializedName("content") val content: List<DrawResponse>? = null
)
