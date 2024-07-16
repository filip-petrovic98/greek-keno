package com.mozzartbet.greekkeno.core.common.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AccurateTimer(
    private val scope: CoroutineScope,
    private val period: Long,
    private val task: () -> Unit
) {
    private var timerJob: Job? = null

    fun startTimer() {
        stopTimer()

        timerJob = scope.launch {
            while (true) {
                val delayUntilNextPeriod = period - System.currentTimeMillis().mod(period)
                delay(delayUntilNextPeriod)

                task()
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
    }
}
