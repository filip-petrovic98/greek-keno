package com.mozzartbet.greekkeno.core.network.datasource

import com.mozzartbet.greekkeno.core.common.functional.Either
import com.mozzartbet.greekkeno.core.model.draws.DrawModel
import com.mozzartbet.greekkeno.core.model.draws.DrawsModel

interface IDrawsDatasource {

    suspend fun getUpcomingDraws(
        gameId: Int,
        numberOfDraws: Int
    ): Either<Exception, List<DrawModel>>

    suspend fun getDraw(
        gameId: Int,
        drawId: Int
    ): Either<Exception, DrawModel>

    suspend fun getDrawsByDate(
        gameId: Int,
        fromDate: String,
        toDate: String
    ): Either<Exception, DrawsModel>
}
