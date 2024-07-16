package com.mozzartbet.greekkeno.feature.game.upcomingdraws.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mozzartbet.greekkeno.core.common.functional.StringHolder
import com.mozzartbet.greekkeno.core.component.GreekKenoAlertDialog
import com.mozzartbet.greekkeno.core.component.GreekKenoProgressIndicator
import com.mozzartbet.greekkeno.core.component.OnLifecycleEvent
import com.mozzartbet.greekkeno.feature.game.R
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.model.RemainingTimeUrgency
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.model.UpcomingDrawUiModel
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.viewmodel.UpcomingDrawsState
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.viewmodel.UpcomingDrawsViewModel
import java.util.Date

@Composable
fun UpcomingDrawsScreen(
    modifier: Modifier = Modifier,
    openDrawDetails: (drawId: Int) -> Unit,
    viewModel: UpcomingDrawsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val showError = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getUpcomingDraws()
    }

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.startTimer()
            }

            Lifecycle.Event.ON_PAUSE -> {
                viewModel.stopTimer()
            }
            else -> {}
        }
    }

    UpcomingScreenContent(
        modifier,
        openDrawDetails,
        state,
        viewModel::getUpcomingDraws
    )

    state.error.consume()?.let {
        showError.value = true
    }

    if (showError.value) {
        GreekKenoAlertDialog(
            title = stringResource(id = R.string.error_title),
            description = stringResource(id = R.string.error_description),
            shouldShowDialog = showError
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UpcomingScreenContent(
    modifier: Modifier = Modifier,
    openDrawDetails: (drawId: Int) -> Unit,
    state: UpcomingDrawsState,
    getUpcomingDraws: (Boolean) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        val pullToRefreshState = rememberPullToRefreshState()

        Box(
            Modifier
                .fillMaxSize()
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {
            LazyColumn(Modifier.padding(top = 16.dp)) {
                items(state.upcomingDraws, key = { it.drawId }) {
                    val remainingTimeColor = when (it.remainingTimeUrgency) {
                        RemainingTimeUrgency.URGENT -> MaterialTheme.colorScheme.error
                        RemainingTimeUrgency.NOT_URGENT -> MaterialTheme.colorScheme.secondary
                    }
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .height(60.dp)
                            .animateItemPlacement(),
                        onClick = { openDrawDetails(it.drawId) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = it.title.getValue(LocalContext.current)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = it.remainingTime,
                                color = remainingTimeColor,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                // Pull to refresh is not working without items
                if (state.upcomingDraws.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(600.dp)
                        ) { }
                    }
                }
            }

            if (pullToRefreshState.isRefreshing) {
                LaunchedEffect(true) {
                    getUpcomingDraws(true)
                }
            }

            LaunchedEffect(state.isRefreshing) {
                if (state.isRefreshing) {
                    pullToRefreshState.startRefresh()
                } else {
                    pullToRefreshState.endRefresh()
                }
            }

            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            if (state.isLoading) {
                GreekKenoProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Preview
@Composable
private fun UpcomingScreenContentPreview() {
    UpcomingScreenContent(
        openDrawDetails = {},
        state = UpcomingDrawsState(
            upcomingDraws = listOf(
                UpcomingDrawUiModel(
                    drawId = 123,
                    title = StringHolder("Kolo u 00:00"),
                    remainingTime = "02:24",
                    remainingTimeUrgency = RemainingTimeUrgency.NOT_URGENT,
                    drawTime = Date(System.currentTimeMillis())
                ),
                UpcomingDrawUiModel(
                    drawId = 124,
                    title = StringHolder("Kolo u 00:00"),
                    remainingTime = "02:24",
                    remainingTimeUrgency = RemainingTimeUrgency.NOT_URGENT,
                    drawTime = Date(System.currentTimeMillis())
                ),
                UpcomingDrawUiModel(
                    drawId = 125,
                    title = StringHolder("Kolo u 00:00"),
                    remainingTime = "02:24",
                    remainingTimeUrgency = RemainingTimeUrgency.NOT_URGENT,
                    drawTime = Date(System.currentTimeMillis())
                )
            )
        )
    ) {
    }
}
