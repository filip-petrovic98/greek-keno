package com.mozzartbet.greekkeno.feature.game.drawresults.model

import android.os.Parcelable
import com.mozzartbet.greekkeno.core.common.functional.StringHolder
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrawResultItemUiModel(
    val drawDateTimeLabel: StringHolder = StringHolder(),
    val drawIdLabel: StringHolder = StringHolder(),
    val selectedBalls: List<Int>
) : Parcelable
