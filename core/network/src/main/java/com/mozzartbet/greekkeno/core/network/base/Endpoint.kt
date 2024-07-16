package com.mozzartbet.greekkeno.core.network.base

internal object Endpoint {
    const val GET_UPCOMING_DRAWS = "draws/v3.0/{gameId}/upcoming/{numberOfDraws}"
    const val GET_DRAW = "draws/v3.0/{gameId}/{drawId}"
    const val GET_DRAWS_BY_DATE = "draws/v3.0/{gameId}/draw-date/{fromDate}/{toDate}"
}
