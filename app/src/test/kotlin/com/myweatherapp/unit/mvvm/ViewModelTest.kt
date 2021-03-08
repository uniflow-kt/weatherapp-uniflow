package com.myweatherapp.unit.mvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.uniflow.core.logger.DebugMessageLogger
import io.uniflow.core.logger.UniFlowLogger
import io.uniflow.test.rule.UniflowDispatchersRule
import org.junit.Rule

abstract class ViewModelTest {

    init {
        UniFlowLogger.init(DebugMessageLogger())
    }

    @get:Rule
    var coroutinesMainDispatcherRule = UniflowDispatchersRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()
}