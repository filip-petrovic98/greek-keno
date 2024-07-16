package com.mozzartbet.greekkeno.core.network.mapper

import com.mozzartbet.greekkeno.core.common.functional.IMapper
import com.mozzartbet.greekkeno.core.model.draws.DrawModel
import com.mozzartbet.greekkeno.core.network.model.DrawResponse
import java.util.Date

internal object DrawMapper : IMapper<DrawResponse, DrawModel> {
    override fun map(from: DrawResponse?): DrawModel {
        return DrawModel(
            gameId = from?.gameId ?: -1,
            drawId = from?.drawId ?: -1,
            drawTime = from?.drawTime?.let { Date(it) },
            status = from?.status.orEmpty(),
            drawBreak = from?.drawBreak ?: 0.0,
            visualDraw = from?.visualDraw ?: 0.0,
            pricePoints = PricePointsMapper.map(from?.pricePoints),
            winningNumbers = WinningNumbers.map(from?.winningNumbers),
            prizeCategories = from?.prizeCategories?.map { PrizeCategoriesMapper.map(it) } ?: emptyList(),
            wagerStatistics = WagerStatisticsMapper.map(from?.wagerStatistics)
        )
    }
}
