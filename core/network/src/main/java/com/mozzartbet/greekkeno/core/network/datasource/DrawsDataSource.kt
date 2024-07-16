package com.mozzartbet.greekkeno.core.network.datasource

import com.mozzartbet.greekkeno.core.common.functional.Either
import com.mozzartbet.greekkeno.core.model.draws.DrawModel
import com.mozzartbet.greekkeno.core.model.draws.DrawsModel
import com.mozzartbet.greekkeno.core.network.apiservice.DrawsApiService
import com.mozzartbet.greekkeno.core.network.base.RemoteDataSource
import com.mozzartbet.greekkeno.core.network.mapper.DrawListMapper
import com.mozzartbet.greekkeno.core.network.mapper.DrawMapper
import com.mozzartbet.greekkeno.core.network.mapper.DrawsMapper
import javax.inject.Inject

internal class DrawsDataSource @Inject constructor(
    private val drawsApiService: DrawsApiService
) : RemoteDataSource(), IDrawsDatasource {

    override suspend fun getUpcomingDraws(
        gameId: Int,
        numberOfDraws: Int
    ): Either<Exception, List<DrawModel>> {
        return getResult(DrawListMapper) {
            drawsApiService.getUpcomingDraws(gameId, numberOfDraws)
        }
    }

    override suspend fun getDraw(
        gameId: Int,
        drawId: Int
    ): Either<Exception, DrawModel> {
        return getResult(DrawMapper) {
            drawsApiService.getDraw(gameId, drawId)
        }
    }

    override suspend fun getDrawsByDate(
        gameId: Int,
        fromDate: String,
        toDate: String
    ): Either<Exception, DrawsModel> {
        return getResult(DrawsMapper) {
            drawsApiService.getDrawsByDate(gameId, fromDate, toDate)
        }
    }
}
