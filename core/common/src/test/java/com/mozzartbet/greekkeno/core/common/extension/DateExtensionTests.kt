package com.mozzartbet.greekkeno.core.common.extension

import java.util.Date
import java.util.concurrent.TimeUnit
import org.junit.Assert.assertEquals
import org.junit.Test

class DateExtensionTests {

    @Test
    fun `test getFormattedTime, negative duration, fallback time returned`() {
        // When
        val result = (-120345L).getFormattedTime()

        // Then
        assertEquals("00:00", result)
    }

    @Test
    fun `test getFormattedTime, hour is 0, time without hours returned`() {
        // When
        val result = (TimeUnit.MINUTES.toMillis(35) + TimeUnit.SECONDS.toMillis(24))
            .getFormattedTime()

        // Then
        assertEquals("35:24", result)
    }

    @Test
    fun `test getFormattedTime, hour is greater than 0, full time format returned`() {
        // When
        val result = (
            TimeUnit.HOURS.toMillis(2) +
                TimeUnit.MINUTES.toMillis(35) +
                TimeUnit.SECONDS.toMillis(24)
            )
            .getFormattedTime()

        // Then
        assertEquals("02:35:24", result)
    }

    @Test
    fun `test getFormattedTime, time returned`() {
        // When
        val result = Date(1721158701140).getFormattedTime()

        // Then
        assertEquals("21:38", result)
    }

    @Test
    fun `test getFormattedDate, date returned`() {
        // When
        val result = Date(1721158701140).getFormattedDate()

        // Then
        assertEquals("2024-07-16", result)
    }

    @Test
    fun `test getFormattedDateTime, date time returned`() {
        // When
        val result = Date(1721158701140).getFormattedDateTime()

        // Then
        assertEquals("16-Jul-2024 21:38", result)
    }
}
