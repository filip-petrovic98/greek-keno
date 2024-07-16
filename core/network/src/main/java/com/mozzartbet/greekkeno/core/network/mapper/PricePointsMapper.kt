package com.mozzartbet.greekkeno.core.network.mapper

import com.mozzartbet.greekkeno.core.common.functional.IMapper
import com.mozzartbet.greekkeno.core.model.draws.PricePointsModel
import com.mozzartbet.greekkeno.core.network.model.PricePointsResponse

internal object PricePointsMapper : IMapper<PricePointsResponse, PricePointsModel> {
    override fun map(from: PricePointsResponse?): PricePointsModel {
        return PricePointsModel(
            addOn = from?.addOn?.map { AddOnMapper.map(it) } ?: emptyList(),
            amount = from?.amount ?: 0.0
        )
    }
}
