package com.mozzartbet.greekkeno.feature.game.drawresults.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mozzartbet.greekkeno.core.common.extension.getFormattedDateTime
import com.mozzartbet.greekkeno.core.common.functional.OneTimeEvent
import com.mozzartbet.greekkeno.core.common.functional.StringHolder
import com.mozzartbet.greekkeno.core.data.repository.draws.IDrawsRepository
import com.mozzartbet.greekkeno.core.model.draws.DrawsModel
import com.mozzartbet.greekkeno.feature.game.R
import com.mozzartbet.greekkeno.feature.game.drawresults.model.DrawResultItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DrawResultsViewModel @Inject constructor(
    private val drawRepository: IDrawsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // region Companion

    companion object {
        const val DRAW_RESULTS = "drawResults"
    }

    // endregion

    // region Properties

    private val loadingState = MutableStateFlow(false)
    private val errorState = MutableStateFlow<OneTimeEvent<Exception>>(OneTimeEvent(null))
    private val drawResultsState =
        savedStateHandle.getStateFlow(DRAW_RESULTS, emptyList<DrawResultItemUiModel>())

    val state = combine(
        drawResultsState,
        loadingState,
        errorState
    ) { drawResults, isLoading, error ->

        DrawResultsState(
            isLoading = isLoading,
            drawResults = drawResults,
            error = error
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, DrawResultsState())

    // endregion

    // region Public API

    fun loadResults(date: Date) {
        loadingState.update { true }

        viewModelScope.launch {
            drawRepository.getDrawsByDate(date)
                .onResult(::handleResponse)
                .onError(::handleError)
        }
    }

    // endregion

    // region Private API

    private fun handleResponse(drawsModel: DrawsModel) {
        loadingState.update { false }

        savedStateHandle[DRAW_RESULTS] = drawsModel.content.map {
            DrawResultItemUiModel(
                drawDateTimeLabel = StringHolder(
                    R.string.draw_time,
                    it.drawTime?.getFormattedDateTime() ?: ""
                ),
                drawIdLabel = StringHolder(R.string.draw_id, it.drawId.toString()),
                selectedBalls = it.winningNumbers.list.sorted()
            )
        }
    }

    private fun handleError(exception: Exception) {
        loadingState.update { false }

        errorState.update {
            OneTimeEvent(exception)
        }
    }

    // endregion
}
