package com.mozzartbet.greekkeno.core.network.mapper

import com.mozzartbet.greekkeno.core.common.functional.IMapper
import com.mozzartbet.greekkeno.core.model.draws.AddOnModel
import com.mozzartbet.greekkeno.core.network.model.AddOnResponse

internal object AddOnMapper : IMapper<AddOnResponse, AddOnModel> {
    override fun map(from: AddOnResponse?): AddOnModel {
        return AddOnModel(
            amount = from?.amount ?: 0.0,
            gameType = from?.gameType.orEmpty()
        )
    }
}
