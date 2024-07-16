package com.mozzartbet.greekkeno.feature.game.upcomingdraws.model

import android.os.Parcelable
import com.mozzartbet.greekkeno.core.common.functional.StringHolder
import java.util.Date
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpcomingDrawUiModel(
    val drawId: Int,
    val title: StringHolder,
    val remainingTime: String,
    val remainingTimeUrgency: RemainingTimeUrgency,
    val drawTime: Date
) : Parcelable
