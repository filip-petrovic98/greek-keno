package com.mozzartbet.greekkeno.core.data.repository.draws

import com.mozzartbet.greekkeno.core.common.functional.Either
import com.mozzartbet.greekkeno.core.model.draws.DrawModel
import com.mozzartbet.greekkeno.core.model.draws.DrawsModel
import com.mozzartbet.greekkeno.core.model.draws.Odd
import java.util.Date

interface IDrawsRepository {
    suspend fun getUpcomingDraws(): Either<Exception, List<DrawModel>>
    suspend fun getDraw(drawId: Int): Either<Exception, DrawModel>
    suspend fun getDrawsByDate(date: Date): Either<Exception, DrawsModel>
    fun getOdds(): List<Odd>
}
