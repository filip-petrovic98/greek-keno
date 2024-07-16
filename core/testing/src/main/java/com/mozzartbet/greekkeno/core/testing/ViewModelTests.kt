package com.mozzartbet.greekkeno.core.testing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mozzartbet.greekkeno.core.testing.rules.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.rules.TestRule

abstract class ViewModelTests {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()
}
