package com.mozzartbet.greekkeno.core.data.repository.draws

import com.mozzartbet.greekkeno.core.common.di.coroutine.IoDispatcher
import com.mozzartbet.greekkeno.core.network.datasource.IDrawsDatasource
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
}
