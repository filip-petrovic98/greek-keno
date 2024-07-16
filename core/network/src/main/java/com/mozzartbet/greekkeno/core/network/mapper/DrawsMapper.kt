package com.mozzartbet.greekkeno.core.network.mapper

import com.mozzartbet.greekkeno.core.common.functional.IMapper
import com.mozzartbet.greekkeno.core.model.draws.DrawsModel
import com.mozzartbet.greekkeno.core.network.model.DrawsResponse

internal object DrawsMapper : IMapper<DrawsResponse, DrawsModel> {
    override fun map(from: DrawsResponse?): DrawsModel {
        return DrawsModel(
            content = from?.content?.map { DrawMapper.map(it) } ?: emptyList()
        )
    }
}
