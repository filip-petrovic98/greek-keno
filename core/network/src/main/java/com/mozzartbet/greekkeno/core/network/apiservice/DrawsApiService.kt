package com.mozzartbet.greekkeno.core.network.apiservice

import com.mozzartbet.greekkeno.core.network.base.Endpoint.UPCOMING_DRAWS
import com.mozzartbet.greekkeno.core.network.model.UpcomingDrawResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface DrawsApiService {
    @GET(UPCOMING_DRAWS)
    suspend fun getUpcomingDraws(
        @Path("gameId") gameId: Int,
        @Path("numberOfDraws") numberOfDraws: Int
    ): Response<List<UpcomingDrawResponse>>
}
