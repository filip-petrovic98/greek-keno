package com.mozzartbet.greekkeno.feature.game.drawresults.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mozzartbet.greekkeno.core.common.extension.getFormattedDate
import com.mozzartbet.greekkeno.core.common.functional.StringHolder
import com.mozzartbet.greekkeno.core.component.GreekKenoAlertDialog
import com.mozzartbet.greekkeno.core.component.GreekKenoProgressIndicator
import com.mozzartbet.greekkeno.feature.game.R
import com.mozzartbet.greekkeno.feature.game.common.ui.BallsTable
import com.mozzartbet.greekkeno.feature.game.drawresults.model.DrawResultItemUiModel
import com.mozzartbet.greekkeno.feature.game.drawresults.viewmodel.DrawResultsState
import com.mozzartbet.greekkeno.feature.game.drawresults.viewmodel.DrawResultsViewModel
import java.util.Date

@Composable
fun DrawResultsScreen(
    modifier: Modifier = Modifier,
    selectedDate: Date,
    viewModel: DrawResultsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val showError = remember { mutableStateOf(false) }

    DrawResultsContent(modifier, state)

    LaunchedEffect(selectedDate.getFormattedDate()) {
        viewModel.loadResults(selectedDate)
    }

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

@Composable
fun DrawResultsContent(
    modifier: Modifier = Modifier,
    state: DrawResultsState
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        Box {
            LazyColumn {
                items(state.drawResults) { item ->
                    DrawResultItem(item)
                }
                item {
                    Spacer(modifier = Modifier.height(64.dp))
                }
            }

            if (state.isLoading) {
                GreekKenoProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun DrawResultItem(drawResultItemUiModel: DrawResultItemUiModel) {
    val context = LocalContext.current

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Text(
                text = drawResultItemUiModel.drawDateTimeLabel.getValue(context),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                text = drawResultItemUiModel.drawIdLabel.getValue(context),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        BallsTable(
            modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 24.dp),
            textElementSize = 20.dp,
            textStyle = MaterialTheme.typography.labelLarge,
            numberOfColumns = 6,
            selectedBalls = drawResultItemUiModel.selectedBalls
        )
    }
}

@Preview
@Composable
private fun DrawResultsContentPreview() {
    DrawResultsContent(
        state = DrawResultsState(
            isLoading = false,
            drawResults = listOf(
                DrawResultItemUiModel(
                    drawDateTimeLabel = StringHolder("Vreme izvlacenja: 15-Jul-2024 22:05"),
                    drawIdLabel = StringHolder("Kolo: 8734658734"),
                    selectedBalls = listOf(3, 4, 65, 23, 4)
                ),
                DrawResultItemUiModel(
                    drawDateTimeLabel = StringHolder("Vreme izvlacenja: 15-Jul-2024 22:05"),
                    drawIdLabel = StringHolder("Kolo: 873465654"),
                    selectedBalls = listOf(3, 4, 65, 23, 4, 34, 45, 65, 88, 23)
                )
            )
        )
    )
}
