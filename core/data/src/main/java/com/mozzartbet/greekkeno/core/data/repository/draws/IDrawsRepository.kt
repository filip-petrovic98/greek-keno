package com.mozzartbet.greekkeno.core.data.repository.draws

import com.mozzartbet.greekkeno.core.common.functional.Either
import com.mozzartbet.greekkeno.core.model.draws.UpcomingDrawModel

interface IDrawsRepository {
    suspend fun getUpcomingDraws(): Either<Exception, List<UpcomingDrawModel>>
}
