package com.mozzartbet.greekkeno.core.data.repository

import com.mozzartbet.greekkeno.core.common.functional.Either
import com.mozzartbet.greekkeno.core.data.repository.draws.DrawsRepository
import com.mozzartbet.greekkeno.core.model.draws.DrawModel
import com.mozzartbet.greekkeno.core.model.draws.DrawsModel
import com.mozzartbet.greekkeno.core.model.draws.Odd
import com.mozzartbet.greekkeno.core.network.datasource.IDrawsDatasource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import java.util.Date
import java.util.stream.Collectors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.jeasy.random.EasyRandom
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DrawsRepositoryTests {

    @MockK
    private lateinit var drawsDataSource: IDrawsDatasource

    private lateinit var sut: DrawsRepository

    private val easyRandom = EasyRandom()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        sut = DrawsRepository(
            drawsDataSource,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `test getUpcomingDraws, error occurred, error returned`() = runTest {
        // Given
        val exception = Exception()
        coEvery { drawsDataSource.getUpcomingDraws(any(), any()) }.returns(Either.Error(exception))

        // When
        val result = sut.getUpcomingDraws()

        // Then
        coVerify { drawsDataSource.getUpcomingDraws(1100, 20) }
        assertEquals(Either.Error(exception), result)
    }

    @Test
    fun `test getUpcomingDraws, has response, result returned`() = runTest {
        // Given
        val expectedResult =
            easyRandom.objects(DrawModel::class.java, 5).collect(Collectors.toList()).toList()
        coEvery { drawsDataSource.getUpcomingDraws(any(), any()) }.returns(
            Either.Result(
                expectedResult
            )
        )

        // When
        val result = sut.getUpcomingDraws()

        // Then
        coVerify { drawsDataSource.getUpcomingDraws(1100, 20) }
        assertEquals(Either.Result(expectedResult), result)
    }

    @Test
    fun `test getDraw, error occurred, error returned`() = runTest {
        // Given
        val exception = Exception()
        coEvery { drawsDataSource.getDraw(any(), any()) }.returns(Either.Error(exception))

        // When
        val result = sut.getDraw(555)

        // Then
        coVerify { drawsDataSource.getDraw(1100, 555) }
        assertEquals(Either.Error(exception), result)
    }

    @Test
    fun `test getDraw, has response, result returned`() = runTest {
        // Given
        val expectedResult = easyRandom.nextObject(DrawModel::class.java)
        coEvery { drawsDataSource.getDraw(any(), any()) }.returns(Either.Result(expectedResult))

        // When
        val result = sut.getDraw(555)

        // Then
        coVerify { drawsDataSource.getDraw(1100, 555) }
        assertEquals(Either.Result(expectedResult), result)
    }

    @Test
    fun `test getDrawsByDate, error occurred, error returned`() = runTest {
        // Given
        val date = Date(1721158701140)
        val exception = Exception()
        coEvery { drawsDataSource.getDrawsByDate(any(), any(), any()) }.returns(
            Either.Error(
                exception
            )
        )

        // When
        val result = sut.getDrawsByDate(date)

        // Then
        coVerify { drawsDataSource.getDrawsByDate(1100, "2024-07-16", "2024-07-16") }
        assertEquals(Either.Error(exception), result)
    }

    @Test
    fun `test getDrawsByDate, has response, result returned`() = runTest {
        // Given
        val date = Date(1721158701140)
        val expectedResult = easyRandom.nextObject(DrawsModel::class.java)
        coEvery { drawsDataSource.getDrawsByDate(any(), any(), any()) }.returns(
            Either.Result(
                expectedResult
            )
        )

        // When
        val result = sut.getDrawsByDate(date)

        // Then
        coVerify { drawsDataSource.getDrawsByDate(1100, "2024-07-16", "2024-07-16") }
        assertEquals(Either.Result(expectedResult), result)
    }

    @Test
    fun `test getOdds, has response, odds returned`() {
        // When
        val result = sut.getOdds()

        // Then
        assertEquals(
            listOf(
                Odd(amount = 1, odd = "3.75"),
                Odd(amount = 2, odd = "14"),
                Odd(amount = 3, odd = "65"),
                Odd(amount = 4, odd = "275"),
                Odd(amount = 5, odd = "1350"),
                Odd(amount = 6, odd = "6500"),
                Odd(amount = 7, odd = "25000"),
                Odd(amount = 8, odd = "125000")
            ),
            result
        )
    }
}
