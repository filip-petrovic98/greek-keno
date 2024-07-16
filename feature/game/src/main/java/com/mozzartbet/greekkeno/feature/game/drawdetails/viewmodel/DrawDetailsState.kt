package com.mozzartbet.greekkeno.feature.game.drawdetails.viewmodel

import com.mozzartbet.greekkeno.core.common.functional.OneTimeEvent
import com.mozzartbet.greekkeno.core.common.functional.StringHolder
import com.mozzartbet.greekkeno.feature.game.drawdetails.model.DrawDetailsUiModel

data class DrawDetailsState(
    val drawDetails: DrawDetailsUiModel = DrawDetailsUiModel(),
    val isLoading: Boolean = true,
    val error: OneTimeEvent<StringHolder> = OneTimeEvent(null),
    val shouldPopScreen: Boolean = false
)
