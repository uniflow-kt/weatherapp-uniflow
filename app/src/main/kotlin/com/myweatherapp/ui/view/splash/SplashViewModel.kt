package com.myweatherapp.ui.view.splash

import com.myweatherapp.domain.usecase.weather.LoadCurrentWeather
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.UIState
import io.uniflow.core.result.toState

class SplashViewModel(
        private val loadCurrentWeather: LoadCurrentWeather
) : AndroidDataFlow() {

    init {
        setState { UIState.Empty }
    }

    fun getLastWeather() = stateFlow(
            {
                setState { UIState.Loading }
                setState {
                    loadCurrentWeather()
                            .toState { UIState.Success }
                }
            },
            { error, _ -> UIState.Failed(error = error) })
}