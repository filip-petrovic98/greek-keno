package com.mozzartbet.greekkeno.feature.game.drawdetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.mozzartbet.greekkeno.core.common.extension.getFormattedTime
import com.mozzartbet.greekkeno.core.common.functional.Either
import com.mozzartbet.greekkeno.core.common.functional.OneTimeEvent
import com.mozzartbet.greekkeno.core.common.functional.StringHolder
import com.mozzartbet.greekkeno.core.data.repository.draws.IDrawsRepository
import com.mozzartbet.greekkeno.core.model.draws.DrawModel
import com.mozzartbet.greekkeno.core.model.draws.Odd
import com.mozzartbet.greekkeno.core.testing.ViewModelTests
import com.mozzartbet.greekkeno.feature.game.R
import com.mozzartbet.greekkeno.feature.game.drawdetails.model.DrawDetailsUiModel
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.model.RemainingTimeUrgency
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import java.lang.Exception
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.jeasy.random.EasyRandom
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DrawDetailsViewModelTests : ViewModelTests() {

    private lateinit var sut: DrawDetailsViewModel

    @MockK
    private lateinit var drawsRepository: IDrawsRepository

    private lateinit var savedStateHandle: SavedStateHandle

    private val easyRandom: EasyRandom = EasyRandom()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        savedStateHandle = SavedStateHandle()

        sut = DrawDetailsViewModel(
            drawsRepository,
            savedStateHandle
        )
    }

    @Test
    fun `test loadDraw, error occurred, error returned`() = runTest {
        // Given
        val exception = Exception()
        coEvery { drawsRepository.getDraw(any()) }.returns(Either.Error(exception))
        savedStateHandle["drawId"] = "123"

        val states = mutableListOf<DrawDetailsState>()
        sut.state
            .onEach(states::add)
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        sut.loadDraw()

        // Then

        assertEquals(
            DrawDetailsState(
                drawDetails = DrawDetailsUiModel(),
                isLoading = false,
                error = OneTimeEvent(StringHolder(R.string.error_description)),
                shouldPopScreen = true
            ),
            states.last()
        )
        coVerify { drawsRepository.getDraw(123) }
    }

    @Test
    fun `test loadDraw, has response, result returned`() = runTest {
        // Given
        val time = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(155)
        val drawTime = Date(time)
        val drawModel = easyRandom.nextObject(DrawModel::class.java).copy(
            drawTime = drawTime
        )
        val odds = listOf(Odd(1, "3.75"))
        coEvery { drawsRepository.getDraw(any()) }.returns(Either.Result(drawModel))
        coEvery { drawsRepository.getOdds() }.returns(odds)
        savedStateHandle["drawId"] = "123"

        val states = mutableListOf<DrawDetailsState>()
        sut.state
            .onEach(states::add)
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        sut.loadDraw()

        // Then

        assertEquals(
            DrawDetailsState(
                drawDetails = DrawDetailsUiModel(
                    drawTimeLabel = StringHolder(R.string.draw_time, drawTime.getFormattedTime()),
                    drawIdLabel = StringHolder(R.string.draw_id, drawModel.drawId.toString()),
                    remainingTime = "02:35",
                    remainingTimeUrgency = RemainingTimeUrgency.NOT_URGENT,
                    numberOfSelectedBallsLabel = StringHolder(),
                    selectedBalls = emptyList(),
                    odds = odds,
                    drawTime = drawTime
                ),
                isLoading = false,
                error = OneTimeEvent(null),
                shouldPopScreen = false
            ),
            states.last()
        )
        coVerify { drawsRepository.getDraw(123) }
        coVerify { drawsRepository.getOdds() }
    }

    @Test
    fun `test loadDraw, has response and remaining time is urgent, result returned`() = runTest {
        // Given
        val time = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(55)
        val drawTime = Date(time)
        val drawModel = easyRandom.nextObject(DrawModel::class.java).copy(
            drawTime = drawTime
        )
        val odds = listOf(Odd(1, "3.75"))
        coEvery { drawsRepository.getDraw(any()) }.returns(Either.Result(drawModel))
        coEvery { drawsRepository.getOdds() }.returns(odds)
        savedStateHandle["drawId"] = "123"

        val states = mutableListOf<DrawDetailsState>()
        sut.state
            .onEach(states::add)
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        sut.loadDraw()

        // Then

        assertEquals(
            DrawDetailsState(
                drawDetails = DrawDetailsUiModel(
                    drawTimeLabel = StringHolder(R.string.draw_time, drawTime.getFormattedTime()),
                    drawIdLabel = StringHolder(R.string.draw_id, drawModel.drawId.toString()),
                    remainingTime = "00:55",
                    remainingTimeUrgency = RemainingTimeUrgency.URGENT,
                    numberOfSelectedBallsLabel = StringHolder(),
                    selectedBalls = emptyList(),
                    odds = odds,
                    drawTime = drawTime
                ),
                isLoading = false,
                error = OneTimeEvent(null),
                shouldPopScreen = false
            ),
            states.last()
        )
        coVerify { drawsRepository.getDraw(123) }
        coVerify { drawsRepository.getOdds() }
    }

    @Test
    fun `test toggleBall, ball is not in the list, ball added`() = runTest {
        // Given
        val selectedBalls = listOf(3, 6, 12, 77, 78)
        val drawUiModel = easyRandom.nextObject(DrawDetailsUiModel::class.java).copy(
            selectedBalls = selectedBalls
        )
        savedStateHandle["drawDetails"] = drawUiModel

        val states = mutableListOf<DrawDetailsState>()
        sut.state
            .onEach(states::add)
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        sut.toggleBall(45)

        // Then
        assertEquals(
            listOf(3, 6, 12, 45, 77, 78),
            states.last().drawDetails.selectedBalls
        )
    }

    @Test
    fun `test toggleBall, ball is in the list, ball removed`() = runTest {
        // Given
        val selectedBalls = listOf(3, 6, 12, 77, 78)
        val drawUiModel = easyRandom.nextObject(DrawDetailsUiModel::class.java).copy(
            selectedBalls = selectedBalls
        )
        savedStateHandle["drawDetails"] = drawUiModel

        val states = mutableListOf<DrawDetailsState>()
        sut.state
            .onEach(states::add)
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        sut.toggleBall(12)

        // Then
        assertEquals(
            listOf(3, 6, 77, 78),
            states.last().drawDetails.selectedBalls
        )
    }

    @Test
    fun `test toggleBall, selected balls limit exceeded, error returned`() = runTest {
        // Given
        val selectedBalls = listOf(3, 6, 12, 77, 78, 45, 6, 34, 10, 11, 33, 34, 35, 36, 37)
        val drawUiModel = easyRandom.nextObject(DrawDetailsUiModel::class.java).copy(
            selectedBalls = selectedBalls
        )
        savedStateHandle["drawDetails"] = drawUiModel

        val states = mutableListOf<DrawDetailsState>()
        sut.state
            .onEach(states::add)
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        sut.toggleBall(99)

        // Then
        assertEquals(
            listOf(3, 6, 12, 77, 78, 45, 6, 34, 10, 11, 33, 34, 35, 36, 37),
            states.last().drawDetails.selectedBalls
        )
        assertEquals(
            OneTimeEvent(StringHolder(R.string.max_selected_balls_error)),
            states.last().error
        )
    }
}
