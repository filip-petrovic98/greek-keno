package com.mozzartbet.greekkeno.feature.game.drawdetails.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mozzartbet.greekkeno.core.common.functional.StringHolder
import com.mozzartbet.greekkeno.core.component.GreekKenoAlertDialog
import com.mozzartbet.greekkeno.core.component.GreekKenoProgressIndicator
import com.mozzartbet.greekkeno.core.component.OnLifecycleEvent
import com.mozzartbet.greekkeno.core.model.draws.Odd
import com.mozzartbet.greekkeno.feature.game.R
import com.mozzartbet.greekkeno.feature.game.common.ui.BallsTable
import com.mozzartbet.greekkeno.feature.game.drawdetails.model.DrawDetailsUiModel
import com.mozzartbet.greekkeno.feature.game.drawdetails.viewmodel.DrawDetailsState
import com.mozzartbet.greekkeno.feature.game.drawdetails.viewmodel.DrawDetailsViewModel
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.model.RemainingTimeUrgency
import java.util.Date

@Composable
fun DrawDetailsScreen(
    viewModel: DrawDetailsViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val showError = remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf(StringHolder()) }

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.loadDraw()
                viewModel.startTimer()
            }

            Lifecycle.Event.ON_PAUSE -> {
                viewModel.stopTimer()
            }

            else -> {}
        }
    }

    DrawDetailsContent(state, viewModel::toggleBall)

    state.error.consume()?.let {
        errorMessage = it
        showError.value = true
    }

    if (showError.value) {
        GreekKenoAlertDialog(
            title = stringResource(id = R.string.error_title),
            description = errorMessage.getValue(LocalContext.current),
            shouldShowDialog = showError
        ) {
            if (state.shouldPopScreen) {
                popBackStack()
            }
        }
    }
}

@Composable
fun DrawDetailsContent(
    state: DrawDetailsState,
    toggleBall: (Int) -> Unit
) {
    val context = LocalContext.current

    Scaffold { paddingValues ->

        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box {
                if (state.isLoading) {
                    GreekKenoProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (!state.shouldPopScreen) {
                    LazyColumn {
                        item {
                            HeaderItem(state, context)
                        }

                        item {
                            OddsTable(state = state)
                        }

                        item {
                            TableTitleText(
                                title = stringResource(id = R.string.choose_balls),
                                textAlign = TextAlign.Center
                            )
                        }

                        item {
                            TablePicker(
                                state = state,
                                toggleBall = toggleBall
                            )
                        }

                        if (state.drawDetails.selectedBalls.isNotEmpty()) {
                            item {
                                TableTitleText(
                                    title = state.drawDetails.numberOfSelectedBallsLabel.getValue(
                                        context
                                    )
                                )
                            }
                        }

                        item {
                            SelectedBallsTable(state, toggleBall)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                            .align(Alignment.BottomStart)
                    ) {
                        val remainingTimeColor = when (state.drawDetails.remainingTimeUrgency) {
                            RemainingTimeUrgency.URGENT -> MaterialTheme.colorScheme.error
                            RemainingTimeUrgency.NOT_URGENT -> MaterialTheme.colorScheme.secondary
                        }

                        Text(
                            text = stringResource(id = R.string.remaining_time),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = state.drawDetails.remainingTime,
                            color = remainingTimeColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OddsTable(modifier: Modifier = Modifier, state: DrawDetailsState) {
    LazyRow(modifier = modifier.background(MaterialTheme.colorScheme.primary)) {
        item {
            Column {
                OddTableCell(stringResource(id = R.string.odd_number))
                OddTableCell(stringResource(id = R.string.odd))
            }
        }
        items(state.drawDetails.odds) { odd ->
            Column {
                OddTableCell(odd.amount.toString())
                OddTableCell(odd.odd, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun OddTableCell(label: String, color: Color? = null) {
    Text(
        modifier = Modifier
            .height(32.dp)
            .width(56.dp)
            .border(0.5.dp, Color.White)
            .wrapContentHeight(align = Alignment.CenterVertically),
        text = label,
        style = MaterialTheme.typography.labelSmall,
        textAlign = TextAlign.Center,
        color = color ?: MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
private fun SelectedBallsTable(
    state: DrawDetailsState,
    toggleBall: (Int) -> Unit
) {
    BallsTable(
        modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 64.dp),
        textElementSize = 20.dp,
        textStyle = MaterialTheme.typography.labelLarge,
        numberOfColumns = 6,
        selectedBalls = state.drawDetails.selectedBalls
    ) { ball ->
        toggleBall(ball)
    }
}

@Composable
private fun TableTitleText(title: String, textAlign: TextAlign = TextAlign.Start) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
        text = title,
        textAlign = textAlign
    )
}

@Composable
private fun TablePicker(
    modifier: Modifier = Modifier,
    state: DrawDetailsState,
    toggleBall: (Int) -> Unit
) {
    val balls = mutableListOf<Int>()
    repeat(80) { i ->
        balls.add(i + 1)
    }

    BallsTable(
        modifier = modifier,
        hasBorder = true,
        balls = balls,
        selectedBalls = state.drawDetails.selectedBalls
    ) { ball ->
        toggleBall(ball)
    }
}

@Composable
private fun HeaderItem(
    state: DrawDetailsState,
    context: Context
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = state.drawDetails.drawTimeLabel.getValue(context),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Text(
            text = state.drawDetails.drawIdLabel.getValue(context),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun DrawDetailsContentPreview() {
    val odds = listOf(
        Odd(amount = 1, odd = "3.75"),
        Odd(amount = 2, odd = "14"),
        Odd(amount = 3, odd = "65"),
        Odd(amount = 4, odd = "275"),
        Odd(amount = 5, odd = "1350"),
        Odd(amount = 6, odd = "6500"),
        Odd(amount = 7, odd = "25000"),
        Odd(amount = 8, odd = "125000")
    )

    DrawDetailsContent(
        state = DrawDetailsState(
            drawDetails = DrawDetailsUiModel(
                drawTimeLabel = StringHolder("Vreme izvlacenja: 00:00"),
                drawIdLabel = StringHolder("Kolo: 26347832"),
                remainingTime = "01:35",
                remainingTimeUrgency = RemainingTimeUrgency.NOT_URGENT,
                numberOfSelectedBallsLabel = StringHolder("Odabrano 4 loptice"),
                selectedBalls = listOf(4, 55, 2, 1),
                odds = odds,
                drawTime = Date(System.currentTimeMillis())
            ),
            isLoading = false
        )
    ) {
    }
}
