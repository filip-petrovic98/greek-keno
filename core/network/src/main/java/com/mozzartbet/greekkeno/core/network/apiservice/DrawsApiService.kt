package com.mozzartbet.greekkeno.core.network.apiservice

import com.mozzartbet.greekkeno.core.network.base.Endpoint.GET_DRAW
import com.mozzartbet.greekkeno.core.network.base.Endpoint.GET_DRAWS_BY_DATE
import com.mozzartbet.greekkeno.core.network.base.Endpoint.GET_UPCOMING_DRAWS
import com.mozzartbet.greekkeno.core.network.model.DrawResponse
import com.mozzartbet.greekkeno.core.network.model.DrawsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface DrawsApiService {

    @GET(GET_UPCOMING_DRAWS)
    suspend fun getUpcomingDraws(
        @Path("gameId") gameId: Int,
        @Path("numberOfDraws") numberOfDraws: Int
    ): Response<List<DrawResponse>>

    @GET(GET_DRAW)
    suspend fun getDraw(
        @Path("gameId") gameId: Int,
        @Path("drawId") drawId: Int
    ): Response<DrawResponse>

    @GET(GET_DRAWS_BY_DATE)
    suspend fun getDrawsByDate(
        @Path("gameId") gameId: Int,
        @Path("fromDate") fromDate: String,
        @Path("toDate") toDate: String
    ): Response<DrawsResponse>
}
