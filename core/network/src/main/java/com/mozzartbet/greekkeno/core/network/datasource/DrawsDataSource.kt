package com.mozzartbet.greekkeno.core.network.datasource

import com.mozzartbet.greekkeno.core.common.functional.Either
import com.mozzartbet.greekkeno.core.model.draws.UpcomingDrawModel
import com.mozzartbet.greekkeno.core.network.apiservice.DrawsApiService
import com.mozzartbet.greekkeno.core.network.base.RemoteDataSource
import com.mozzartbet.greekkeno.core.network.mapper.UpcomingDrawsMapper
import javax.inject.Inject

internal class DrawsDataSource @Inject constructor(
    private val drawsApiService: DrawsApiService
) : RemoteDataSource(), IDrawsDatasource {

    override suspend fun getUpcomingDraws(
        gameId: Int,
        numberOfDraws: Int
    ): Either<Exception, List<UpcomingDrawModel>> {
        return getResult(UpcomingDrawsMapper) { drawsApiService.getUpcomingDraws(gameId, numberOfDraws) }
    }
}
