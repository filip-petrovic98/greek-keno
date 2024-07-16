package com.mozzartbet.greekkeno.feature.game.drawresults.viewmodel

import com.mozzartbet.greekkeno.core.common.functional.OneTimeEvent
import com.mozzartbet.greekkeno.feature.game.drawresults.model.DrawResultItemUiModel

data class DrawResultsState(
    val isLoading: Boolean = false,
    val drawResults: List<DrawResultItemUiModel> = emptyList(),
    val error: OneTimeEvent<Exception> = OneTimeEvent(null)
)
