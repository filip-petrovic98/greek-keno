package com.mozzartbet.greekkeno.core.network.mapper

import com.mozzartbet.greekkeno.core.common.functional.IMapper
import com.mozzartbet.greekkeno.core.model.draws.WinningNumbersModel
import com.mozzartbet.greekkeno.core.network.model.WinningNumbersResponse

internal object WinningNumbers : IMapper<WinningNumbersResponse, WinningNumbersModel> {
    override fun map(from: WinningNumbersResponse?): WinningNumbersModel {
        return WinningNumbersModel(
            list = from?.list ?: emptyList(),
            bonus = from?.bonus ?: emptyList(),
            sideBets = SideBetsMapper.map(from?.sideBets)
        )
    }
}
