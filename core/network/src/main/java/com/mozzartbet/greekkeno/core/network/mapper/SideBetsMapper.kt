package com.mozzartbet.greekkeno.core.network.mapper

import com.mozzartbet.greekkeno.core.common.functional.IMapper
import com.mozzartbet.greekkeno.core.model.draws.SideBetsModel
import com.mozzartbet.greekkeno.core.network.model.SideBetsResponse

internal object SideBetsMapper : IMapper<SideBetsResponse, SideBetsModel> {
    override fun map(from: SideBetsResponse?): SideBetsModel {
        return SideBetsModel(
            evenNumbersCount = from?.evenNumbersCount ?: 0,
            oddNumbersCount = from?.oddNumbersCount ?: 0,
            winningColumn = from?.winningColumn ?: 0,
            winningParity = from?.winningParity.orEmpty(),
            oddNumbers = from?.oddNumbers ?: emptyList(),
            evenNumbers = from?.evenNumbers ?: emptyList()
        )
    }
}
