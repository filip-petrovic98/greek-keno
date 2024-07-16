package com.mozzartbet.greekkeno.core.model.draws

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Odd(
    val amount: Int,
    val odd: String
) : Parcelable
