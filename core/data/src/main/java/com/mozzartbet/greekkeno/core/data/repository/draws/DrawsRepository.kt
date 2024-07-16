package com.mozzartbet.greekkeno.core.data.repository.draws

import com.mozzartbet.greekkeno.core.common.di.coroutine.IoDispatcher
import com.mozzartbet.greekkeno.core.common.extension.getFormattedDate
import com.mozzartbet.greekkeno.core.common.functional.Either
import com.mozzartbet.greekkeno.core.model.draws.DrawModel
import com.mozzartbet.greekkeno.core.model.draws.DrawsModel
import com.mozzartbet.greekkeno.core.model.draws.Odd
import com.mozzartbet.greekkeno.core.network.datasource.IDrawsDatasource
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class DrawsRepository @Inject constructor(
    private val drawsDataSource: IDrawsDatasource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : IDrawsRepository {

    companion object {
        const val GAME_ID = 1100
        const val NUMBER_OF_DRAWS = 20
    }

    override suspend fun getUpcomingDraws() = withContext(dispatcher) {
        drawsDataSource.getUpcomingDraws(GAME_ID, NUMBER_OF_DRAWS)
    }

    override suspend fun getDraw(
        drawId: Int
    ): Either<Exception, DrawModel> = withContext(dispatcher) {
        drawsDataSource.getDraw(GAME_ID, drawId)
    }

    override suspend fun getDrawsByDate(
        date: Date
    ): Either<Exception, DrawsModel> = withContext(dispatcher) {
        drawsDataSource.getDrawsByDate(GAME_ID, date.getFormattedDate(), date.getFormattedDate())
    }

    override fun getOdds(): List<Odd> {
        return listOf(
            Odd(amount = 1, odd = "3.75"),
            Odd(amount = 2, odd = "14"),
            Odd(amount = 3, odd = "65"),
            Odd(amount = 4, odd = "275"),
            Odd(amount = 5, odd = "1350"),
            Odd(amount = 6, odd = "6500"),
            Odd(amount = 7, odd = "25000"),
            Odd(amount = 8, odd = "125000")
        )
    }
}
