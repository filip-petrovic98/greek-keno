package com.mozzartbet.greekkeno.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mozzartbet.greekkeno.core.component.GreekKenoDatePicker
import com.mozzartbet.greekkeno.core.component.NonFutureSelectableDates
import com.mozzartbet.greekkeno.feature.game.drawresults.ui.DrawResultsScreen
import com.mozzartbet.greekkeno.feature.game.livedraw.ui.LiveDrawScreen
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.ui.UpcomingDrawsScreen
import com.mozzartbet.greekkeno.navigation.BottomNavItem
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(openDrawDetails: (drawId: Int) -> Unit) {
    var selectedNavItem by rememberSaveable {
        mutableStateOf(BottomNavItem.UPCOMING_DRAWS)
    }
    val resultsDate = rememberSaveable { mutableStateOf(Date(System.currentTimeMillis())) }
    val datePickerState = rememberDatePickerState(
        selectableDates = NonFutureSelectableDates()
    )
    val showDatePicker = remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomNavItem.entries.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedNavItem.ordinal == index,
                        onClick = {
                            selectedNavItem = BottomNavItem.entries[index]
                        },
                        label = {
                            Text(text = stringResource(id = item.labelRes))
                        },
                        alwaysShowLabel = true,
                        icon = {
                            Icon(
                                imageVector = if (index == selectedNavItem.ordinal) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                },
                                contentDescription = stringResource(id = item.labelRes)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.secondary,
                            selectedIconColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedNavItem == BottomNavItem.DRAW_RESULTS) {
                FloatingActionButton(onClick = {
                    showDatePicker.value = true
                }) {
                    Icon(Icons.Filled.CalendarMonth, "Calendar")
                }
            }
        }
    ) { paddingValues ->
        when (selectedNavItem) {
            BottomNavItem.UPCOMING_DRAWS -> UpcomingDrawsScreen(
                Modifier.padding(paddingValues),
                openDrawDetails
            )

            BottomNavItem.LIVE_DRAW -> LiveDrawScreen(Modifier.padding(paddingValues))
            BottomNavItem.DRAW_RESULTS -> DrawResultsScreen(
                modifier = Modifier.padding(paddingValues),
                selectedDate = resultsDate.value
            )
        }

        if (showDatePicker.value) {
            GreekKenoDatePicker(showDatePicker, datePickerState, resultsDate)
        }
    }
}
