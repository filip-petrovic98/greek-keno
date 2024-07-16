package com.mozzartbet.greekkeno.core.network.mapper

import com.mozzartbet.greekkeno.core.common.functional.IMapper
import com.mozzartbet.greekkeno.core.model.draws.DrawModel
import com.mozzartbet.greekkeno.core.network.model.DrawResponse

internal object DrawListMapper : IMapper<List<DrawResponse>, List<DrawModel>> {
    override fun map(from: List<DrawResponse>?): List<DrawModel> {
        return from?.map { DrawMapper.map(it) } ?: emptyList()
    }
}
