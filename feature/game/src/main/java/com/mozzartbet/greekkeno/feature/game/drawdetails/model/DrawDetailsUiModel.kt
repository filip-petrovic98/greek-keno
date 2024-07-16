package com.mozzartbet.greekkeno.feature.game.drawdetails.model

import android.os.Parcelable
import com.mozzartbet.greekkeno.core.common.functional.StringHolder
import com.mozzartbet.greekkeno.core.model.draws.Odd
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.model.RemainingTimeUrgency
import java.util.Date
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrawDetailsUiModel(
    val drawTimeLabel: StringHolder = StringHolder(),
    val drawIdLabel: StringHolder = StringHolder(),
    val remainingTime: String = "",
    val remainingTimeUrgency: RemainingTimeUrgency = RemainingTimeUrgency.NOT_URGENT,
    val numberOfSelectedBallsLabel: StringHolder = StringHolder(),
    val selectedBalls: List<Int> = emptyList(),
    val odds: List<Odd> = emptyList(),
    val drawTime: Date? = null
) : Parcelable
