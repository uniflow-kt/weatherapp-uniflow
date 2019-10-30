package com.myweatherapp.ui.view.splash

import com.myweatherapp.domain.usecase.weather.LoadCurrentWeather
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.UIState

class SplashViewModel(
        private val loadCurrentWeather: LoadCurrentWeather
) : AndroidDataFlow() {

    init {
        setState { UIState.Empty }
    }

    fun getLastWeather() = setState(
            {
                loadCurrentWeather().toState { UIState.Success }
            },
            { error -> UIState.Failed(error = error) })
}