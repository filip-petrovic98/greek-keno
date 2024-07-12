package com.mozzartbet.greekkeno.core.network.mapper

import com.mozzartbet.greekkeno.core.common.functional.IMapper
import com.mozzartbet.greekkeno.core.model.draws.UpcomingDrawModel
import com.mozzartbet.greekkeno.core.network.model.UpcomingDrawResponse

internal object UpcomingDrawsMapper : IMapper<List<UpcomingDrawResponse>, List<UpcomingDrawModel>> {
    override fun map(from: List<UpcomingDrawResponse>?): List<UpcomingDrawModel> {
        return from?.map { UpcomingDrawMapper.map(it) } ?: emptyList()
    }
}
