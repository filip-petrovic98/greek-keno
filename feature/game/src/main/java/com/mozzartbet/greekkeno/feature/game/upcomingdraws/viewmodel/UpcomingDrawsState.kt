package com.mozzartbet.greekkeno.feature.game.upcomingdraws.viewmodel

import com.mozzartbet.greekkeno.core.common.functional.OneTimeEvent
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.model.UpcomingDrawUiModel

data class UpcomingDrawsState(
    val upcomingDraws: List<UpcomingDrawUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: OneTimeEvent<Exception> = OneTimeEvent(null)
)
