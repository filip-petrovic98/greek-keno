package com.mozzartbet.greekkeno.core.network.mapper

import com.mozzartbet.greekkeno.core.common.functional.IMapper
import com.mozzartbet.greekkeno.core.model.draws.UpcomingDrawModel
import com.mozzartbet.greekkeno.core.network.model.UpcomingDrawResponse

internal object UpcomingDrawMapper : IMapper<UpcomingDrawResponse, UpcomingDrawModel> {
    override fun map(from: UpcomingDrawResponse?): UpcomingDrawModel {
        return UpcomingDrawModel(
            gameId = from?.gameId ?: -1,
            drawId = from?.drawId ?: -1
        )
    }
}
