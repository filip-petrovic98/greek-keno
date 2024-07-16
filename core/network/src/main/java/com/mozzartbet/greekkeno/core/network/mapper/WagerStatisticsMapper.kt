package com.mozzartbet.greekkeno.core.network.mapper

import com.mozzartbet.greekkeno.core.common.functional.IMapper
import com.mozzartbet.greekkeno.core.model.draws.WagerStatisticsModel
import com.mozzartbet.greekkeno.core.network.model.WagerStatisticsResponse

internal object WagerStatisticsMapper : IMapper<WagerStatisticsResponse, WagerStatisticsModel> {
    override fun map(from: WagerStatisticsResponse?): WagerStatisticsModel {
        return WagerStatisticsModel(
            columns = from?.columns ?: 0.0,
            wagers = from?.wagers ?: 0.0,
            addOn = from?.addOn ?: emptyList()
        )
    }
}
