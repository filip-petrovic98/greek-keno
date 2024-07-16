package com.mozzartbet.greekkeno.core.common.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun Long.getFormattedTime(): String {
    return toDuration(DurationUnit.MILLISECONDS).toComponents { hour, minutes, seconds, _ ->

        if (hour < 0 || minutes < 0 || seconds < 0) return "00:00"

        if (hour == 0L) {
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        } else {
            String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minutes, seconds)
        }
    }
}

fun Date.getFormattedTime(): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    return formatter.format(this)
}

fun Date.getFormattedDateTime(): String {
    val formatter = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.ENGLISH)
    return formatter.format(this)
}

fun Date.getFormattedDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return formatter.format(this)
}
