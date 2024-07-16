package com.mozzartbet.greekkeno.feature.game.upcomingdraws.viewmodel

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
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.model.RemainingTimeUrgency
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.model.UpcomingDrawUiModel
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
class UpcomingDrawsViewModel @Inject constructor(
    private val drawsRepository: IDrawsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // region Companion

    companion object {
        const val UPCOMING_DRAWS_KEY = "upcomingDraws"
    }

    // endregion

    // region Properties

    private val timer = AccurateTimer(viewModelScope, 1000L) {
        refreshDraws()
    }

    private val loadingState = MutableStateFlow(false)
    private val refreshingState = MutableStateFlow(false)
    private val errorState = MutableStateFlow<OneTimeEvent<Exception>>(OneTimeEvent(null))
    private val upcomingDraws =
        savedStateHandle.getStateFlow(UPCOMING_DRAWS_KEY, emptyList<UpcomingDrawUiModel>())

    val state = combine(
        loadingState,
        refreshingState,
        errorState,
        upcomingDraws
    ) { isLoading, isRefreshing, error, upcomingDraws ->

        UpcomingDrawsState(
            upcomingDraws = upcomingDraws,
            isLoading = isLoading,
            isRefreshing = isRefreshing,
            error = error
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, UpcomingDrawsState())

    // endregion

    // region Public API

    fun getUpcomingDraws(refresh: Boolean = false) {
        if (refresh) {
            refreshingState.update { true }
        } else {
            loadingState.update { true }
        }

        viewModelScope.launch {
            drawsRepository.getUpcomingDraws()
                .onError(::handleUpcomingDrawsError)
                .onResult(::handleUpcomingDrawsResponse)
        }
    }

    fun stopTimer() {
        timer.stopTimer()
    }

    fun startTimer() {
        timer.startTimer()
    }

    // endregion

    // region Private API

    private fun handleUpcomingDrawsResponse(draws: List<DrawModel>) {
        loadingState.update { false }
        refreshingState.update { false }
        savedStateHandle[UPCOMING_DRAWS_KEY] = draws.mapNotNull {
            createUpcomingDrawUiModel(it.drawId, it.drawTime)
        }
    }

    private fun handleUpcomingDrawsError(exception: Exception) {
        loadingState.update { false }
        refreshingState.update { false }
        errorState.update { OneTimeEvent(exception) }
    }

    private fun calculateRemainingTime(date: Date): Long {
        var time = (date.time - System.currentTimeMillis())
        time += 1000
        return time
    }

    private fun createUpcomingDrawUiModel(drawId: Int, drawTime: Date?): UpcomingDrawUiModel? {
        val remainingTime = calculateRemainingTime(drawTime ?: Date())

        if (remainingTime < 0) return null

        val remainingTimeUrgency = if (remainingTime < URGENCY_TIME) {
            RemainingTimeUrgency.URGENT
        } else {
            RemainingTimeUrgency.NOT_URGENT
        }

        return UpcomingDrawUiModel(
            drawId = drawId,
            title = StringHolder(R.string.upcoming_draw_title, drawTime?.getFormattedTime() ?: ""),
            remainingTime = remainingTime.getFormattedTime(),
            remainingTimeUrgency = remainingTimeUrgency,
            drawTime = drawTime ?: Date()
        )
    }

    private fun refreshDraws() {
        savedStateHandle[UPCOMING_DRAWS_KEY] = state.value.upcomingDraws.mapNotNull {
            createUpcomingDrawUiModel(it.drawId, it.drawTime)
        }
    }

    // endregion
}
