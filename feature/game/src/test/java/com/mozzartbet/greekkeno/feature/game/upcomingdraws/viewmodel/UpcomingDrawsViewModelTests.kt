package com.mozzartbet.greekkeno.feature.game.upcomingdraws.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.mozzartbet.greekkeno.core.common.extension.getFormattedTime
import com.mozzartbet.greekkeno.core.common.functional.Either
import com.mozzartbet.greekkeno.core.common.functional.OneTimeEvent
import com.mozzartbet.greekkeno.core.common.functional.StringHolder
import com.mozzartbet.greekkeno.core.data.repository.draws.IDrawsRepository
import com.mozzartbet.greekkeno.core.model.draws.DrawModel
import com.mozzartbet.greekkeno.core.testing.ViewModelTests
import com.mozzartbet.greekkeno.feature.game.R
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.model.RemainingTimeUrgency
import com.mozzartbet.greekkeno.feature.game.upcomingdraws.model.UpcomingDrawUiModel
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpcomingDrawsViewModelTests : ViewModelTests() {

    private lateinit var sut: UpcomingDrawsViewModel

    @MockK
    private lateinit var drawsRepository: IDrawsRepository

    private lateinit var savedStateHandle: SavedStateHandle

    private val easyRandom: EasyRandom = EasyRandom()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        savedStateHandle = SavedStateHandle()

        sut = UpcomingDrawsViewModel(
            drawsRepository,
            savedStateHandle
        )
    }

    @Test
    fun `test getUpcomingDraws, error occurred, error returned`() = runTest {
        // Given
        val exception = Exception()
        coEvery { drawsRepository.getUpcomingDraws() }.returns(Either.Error(exception))

        val states = mutableListOf<UpcomingDrawsState>()
        sut.state
            .onEach(states::add)
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        sut.getUpcomingDraws()

        // Then

        assertEquals(
            UpcomingDrawsState(
                upcomingDraws = emptyList(),
                isLoading = false,
                isRefreshing = false,
                error = OneTimeEvent(exception)
            ),
            states.last()
        )
        coVerify { drawsRepository.getUpcomingDraws() }
    }

    @Test
    fun `test getUpcomingDraws, loading and has response, response returned`() = runTest {
        // Given
        val time = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(155)
        val drawTime = Date(time)
        val drawModel = easyRandom.nextObject(DrawModel::class.java).copy(
            drawTime = drawTime
        )

        coEvery { drawsRepository.getUpcomingDraws() }.returns(Either.Result(listOf(drawModel)))

        val states = mutableListOf<UpcomingDrawsState>()
        sut.state
            .onEach(states::add)
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        sut.getUpcomingDraws(refresh = false)

        // Then
        assertTrue(states[1].isLoading)
        assertFalse(states[1].isRefreshing)
        assertEquals(
            UpcomingDrawsState(
                upcomingDraws = listOf(
                    UpcomingDrawUiModel(
                        drawId = drawModel.drawId,
                        title = StringHolder(
                            R.string.upcoming_draw_title,
                            drawTime.getFormattedTime()
                        ),
                        remainingTime = "02:35",
                        remainingTimeUrgency = RemainingTimeUrgency.NOT_URGENT,
                        drawTime = drawModel.drawTime!!
                    )
                ),
                isLoading = false,
                isRefreshing = false,
                error = OneTimeEvent(null)
            ),
            states.last()
        )
        coVerify { drawsRepository.getUpcomingDraws() }
    }

    @Test
    fun `test getUpcomingDraws, refreshing and has response, result returned`() = runTest {
        // Given
        val time = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(155)
        val drawTime = Date(time)
        val drawModel = easyRandom.nextObject(DrawModel::class.java).copy(
            drawTime = drawTime
        )

        coEvery { drawsRepository.getUpcomingDraws() }.returns(Either.Result(listOf(drawModel)))

        val states = mutableListOf<UpcomingDrawsState>()
        sut.state
            .onEach(states::add)
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        sut.getUpcomingDraws(refresh = true)

        // Then
        assertTrue(states[1].isRefreshing)
        assertFalse(states[1].isLoading)
        assertEquals(
            UpcomingDrawsState(
                upcomingDraws = listOf(
                    UpcomingDrawUiModel(
                        drawId = drawModel.drawId,
                        title = StringHolder(
                            R.string.upcoming_draw_title,
                            drawTime.getFormattedTime()
                        ),
                        remainingTime = "02:35",
                        remainingTimeUrgency = RemainingTimeUrgency.NOT_URGENT,
                        drawTime = drawModel.drawTime!!
                    )
                ),
                isLoading = false,
                isRefreshing = false,
                error = OneTimeEvent(null)
            ),
            states.last()
        )
        coVerify { drawsRepository.getUpcomingDraws() }
    }

    @Test
    fun `test getUpcomingDraws, has response and remaining time is urgent, result returned`() =
        runTest {
            // Given
            val time = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(55)
            val drawTime = Date(time)
            val drawModel = easyRandom.nextObject(DrawModel::class.java).copy(
                drawTime = drawTime
            )

            coEvery { drawsRepository.getUpcomingDraws() }.returns(Either.Result(listOf(drawModel)))

            val states = mutableListOf<UpcomingDrawsState>()
            sut.state
                .onEach(states::add)
                .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

            // When
            sut.getUpcomingDraws(refresh = false)

            // Then
            assertTrue(states[1].isLoading)
            assertFalse(states[1].isRefreshing)
            assertEquals(
                UpcomingDrawsState(
                    upcomingDraws = listOf(
                        UpcomingDrawUiModel(
                            drawId = drawModel.drawId,
                            title = StringHolder(
                                R.string.upcoming_draw_title,
                                drawTime.getFormattedTime()
                            ),
                            remainingTime = "00:55",
                            remainingTimeUrgency = RemainingTimeUrgency.URGENT,
                            drawTime = drawModel.drawTime!!
                        )
                    ),
                    isLoading = false,
                    isRefreshing = false,
                    error = OneTimeEvent(null)
                ),
                states.last()
            )
            coVerify { drawsRepository.getUpcomingDraws() }
        }
}
