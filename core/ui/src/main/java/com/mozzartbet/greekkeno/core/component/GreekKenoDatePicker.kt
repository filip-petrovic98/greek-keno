@file:OptIn(ExperimentalMaterial3Api::class)

package com.mozzartbet.greekkeno.core.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import java.util.Calendar
import java.util.Date

@Composable
fun GreekKenoDatePicker(
    showDatePicker: MutableState<Boolean>,
    datePickerState: DatePickerState,
    resultsDate: MutableState<Date>
) {
    DatePickerDialog(
        onDismissRequest = {
            showDatePicker.value = false
            datePickerState.selectedDateMillis = null
        },
        confirmButton = {
            TextButton(
                onClick = {
                    resultsDate.value = Date(datePickerState.selectedDateMillis ?: 0)
                    showDatePicker.value = false
                    datePickerState.selectedDateMillis = null
                },
                enabled = datePickerState.selectedDateMillis != null
            ) {
                Text(text = "Confirm", color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                showDatePicker.value = false
                datePickerState.selectedDateMillis = null
            }) {
                Text(text = "Dismiss", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedDayContentColor = MaterialTheme.colorScheme.secondary,
                todayContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

class NonFutureSelectableDates : SelectableDates {
    override fun isSelectableYear(year: Int): Boolean {
        return year <= Calendar.getInstance().get(Calendar.YEAR)
    }

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis <= System.currentTimeMillis()
    }
}
