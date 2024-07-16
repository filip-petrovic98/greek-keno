package com.mozzartbet.greekkeno.feature.game.drawdetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mozzartbet.greekkeno.core.common.extension.getFormattedTime
import com.mozzartbet.greekkeno.core.common.functional.OneTimeEvent
import com.mozzartbet.greekkeno.core.common.functional.StringHolder
import com.mozzartbet.greekkeno.core.common.timer.AccurateTimer
import com.mozzartbet.greekkeno.core.data.repository.draws.IDrawsRepository
import com.mozzartbet.greekkeno.core.model.draws.DrawModel
import com.mozzartbet.greekkeno.feature.game.R
import com.mozzartbet.greekkeno.feature.game.common.model.Constants.URGENCY_TIME
import com.mozzartbet.greekkeno.feature.game.drawdetails.model.DrawDetailsUiModel
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.model.RemainingTimeUrgency
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DrawDetailsViewModel @Inject constructor(
    private val drawRepository: IDrawsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // region Companion

    companion object {
        const val DRAW_DETAILS = "drawDetails"
        const val MAX_SELECTED_BALLS = 15
    }

    // endregion

    // region Properties

    private val timer = AccurateTimer(viewModelScope, 1000L) {
        refreshRemainingTime()
    }

    private val loadingState = MutableStateFlow(true)
    private val errorState = MutableStateFlow<OneTimeEvent<StringHolder>>(OneTimeEvent(null))
    private val shouldPopScreenState = MutableStateFlow(false)
    private val drawDetailsState = savedStateHandle.getStateFlow(DRAW_DETAILS, DrawDetailsUiModel())

    val state = combine(
        loadingState,
        errorState,
        drawDetailsState,
        shouldPopScreenState
    ) { isLoading, error, drawDetails, shouldPopScreen ->

        DrawDetailsState(
            drawDetails = drawDetails,
            isLoading = isLoading,
            error = error,
            shouldPopScreen = shouldPopScreen
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, DrawDetailsState())

    // endregion

    // region Public API

    fun loadDraw() {
        // Don't load again if it is not necessary
        if (state.value.drawDetails.odds.isNotEmpty()) {
            loadingState.update { false }
            return
        }

        loadingState.update { true }

        viewModelScope.launch {
            savedStateHandle.get<String?>("drawId")?.toIntOrNull()?.let {
                drawRepository.getDraw(it)
                    .onResult(::handleDrawResponse)
                    .onError(::handleDrawError)
            }
        }
    }

    fun toggleBall(ball: Int) {
        if (state.value.drawDetails.selectedBalls.size == MAX_SELECTED_BALLS &&
            !state.value.drawDetails.selectedBalls.contains(ball)
        ) {
            errorState.update { OneTimeEvent(StringHolder(R.string.max_selected_balls_error)) }
            return
        }

        val selectedBalls = state.value.drawDetails.selectedBalls.toMutableList()

        if (state.value.drawDetails.selectedBalls.contains(ball)) {
            selectedBalls.remove(ball)
        } else {
            selectedBalls.add(ball)
        }
        selectedBalls.sort()

        savedStateHandle[DRAW_DETAILS] = state.value.drawDetails.copy(
            selectedBalls = selectedBalls,
            numberOfSelectedBallsLabel = StringHolder(
                stringRes = R.plurals.number_of_selected_balls,
                quantity = selectedBalls.size,
                selectedBalls.size
            )
        )
    }

    fun stopTimer() {
        timer.stopTimer()
    }

    fun startTimer() {
        timer.startTimer()
    }

    // endregion

    // region Private API

    private fun handleDrawResponse(draw: DrawModel) {
        loadingState.update { false }

        val remainingTime = calculateRemainingTime(draw.drawTime ?: Date())

        savedStateHandle[DRAW_DETAILS] = state.value.drawDetails.copy(
            drawTimeLabel = StringHolder(
                R.string.draw_time,
                draw.drawTime?.getFormattedTime().orEmpty()
            ),
            drawIdLabel = StringHolder(R.string.draw_id, draw.drawId.toString()),
            remainingTime = remainingTime.getFormattedTime(),
            remainingTimeUrgency = if (remainingTime < URGENCY_TIME) {
                RemainingTimeUrgency.URGENT
            } else {
                RemainingTimeUrgency.NOT_URGENT
            },
            odds = drawRepository.getOdds(),
            drawTime = draw.drawTime ?: Date()
        )
    }

    private fun handleDrawError(exception: Exception) {
        loadingState.update { false }
        shouldPopScreenState.update { true }
        errorState.update { OneTimeEvent(StringHolder(R.string.error_description)) }
    }

    private fun refreshRemainingTime() {
        val remainingTime = calculateRemainingTime(state.value.drawDetails.drawTime ?: Date())

        savedStateHandle[DRAW_DETAILS] = state.value.drawDetails.copy(
            remainingTime = remainingTime.getFormattedTime(),
            remainingTimeUrgency = if (remainingTime < URGENCY_TIME) {
                RemainingTimeUrgency.URGENT
            } else {
                RemainingTimeUrgency.NOT_URGENT
            }
        )
    }

    private fun calculateRemainingTime(date: Date): Long {
        var time = (date.time - System.currentTimeMillis())
        time += 1000
        return time
    }

    // endregion
}
