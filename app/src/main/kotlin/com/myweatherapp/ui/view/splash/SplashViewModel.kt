package com.myweatherapp.ui.view.splash

import com.myweatherapp.domain.usecase.weather.LoadCurrentWeather
import io.uniflow.android.AndroidDataFlow
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState

class SplashViewModel(
    private val loadCurrentWeather: LoadCurrentWeather
) : AndroidDataFlow() {

    fun getLastWeather() = action(
        onAction = {
            sendEvent { UIEvent.Loading }
            loadCurrentWeather()
            setState { UIState.Success }
        },
        onError = { error, _ -> setState { UIState.Failed("getLastWeather failed", error) } })
}