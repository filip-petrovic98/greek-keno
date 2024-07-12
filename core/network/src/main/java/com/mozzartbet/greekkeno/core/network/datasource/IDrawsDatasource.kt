package com.mozzartbet.greekkeno.core.network.datasource

import com.mozzartbet.greekkeno.core.common.functional.Either
import com.mozzartbet.greekkeno.core.model.draws.UpcomingDrawModel

interface IDrawsDatasource {

    suspend fun getUpcomingDraws(
        gameId: Int,
        numberOfDraws: Int
    ): Either<Exception, List<UpcomingDrawModel>>
}
