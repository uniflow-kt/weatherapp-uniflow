package com.myweatherapp.unit.mvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.uniflow.core.logger.DebugMessageLogger
import io.uniflow.core.logger.UniFlowLogger
import io.uniflow.test.rule.TestDispatchersRule
import org.junit.Rule

abstract class ViewModelTest {

    init {
        UniFlowLogger.init(DebugMessageLogger())
    }

    @get:Rule
    var coroutinesMainDispatcherRule = TestDispatchersRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()
}