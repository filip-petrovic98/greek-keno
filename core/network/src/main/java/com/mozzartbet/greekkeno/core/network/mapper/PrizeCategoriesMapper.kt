package com.mozzartbet.greekkeno.core.network.mapper

import com.mozzartbet.greekkeno.core.common.functional.IMapper
import com.mozzartbet.greekkeno.core.model.draws.PrizeCategoriesModel
import com.mozzartbet.greekkeno.core.network.model.PrizeCategoriesResponse

internal object PrizeCategoriesMapper : IMapper<PrizeCategoriesResponse, PrizeCategoriesModel> {
    override fun map(from: PrizeCategoriesResponse?): PrizeCategoriesModel {
        return PrizeCategoriesModel(
            id = from?.id ?: -1,
            divident = from?.divident ?: 0.0,
            winners = from?.winners ?: 0.0,
            distributed = from?.distributed ?: 0.0,
            jackpot = from?.jackpot ?: 0.0,
            fixed = from?.fixed ?: 0.0,
            categoryType = from?.categoryType ?: 0.0,
            gameType = from?.gameType.orEmpty()
        )
    }
}
