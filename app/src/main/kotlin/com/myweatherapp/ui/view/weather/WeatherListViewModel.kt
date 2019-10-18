package com.myweatherapp.ui.view.weather

import com.myweatherapp.domain.usecase.weather.GetCurrentWeather
import com.myweatherapp.domain.usecase.weather.GetWeatherForGivenLocation
import com.myweatherapp.ui.view.weather.uimodel.WeatherListState
import com.myweatherapp.ui.view.weather.uimodel.WeatherListUIEvent
import com.myweatherapp.ui.view.weather.uimodel.mapToWeatherListState
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.UIState
import io.uniflow.core.flow.fromState
import io.uniflow.core.result.onFailure
import io.uniflow.core.result.toState
import io.uniflow.core.result.toStateOrNull

class WeatherListViewModel(
        private val getCurrentWeather: GetCurrentWeather,
        private val getWeatherForLocation: GetWeatherForGivenLocation
) : AndroidDataFlow() {

    init {
        setState { UIState.Empty }
    }

    fun loadNewLocation(location: String) = fromState<WeatherListState> {
        sendEvent(WeatherListUIEvent.ProceedLocation(location))
        getWeatherForLocation(location)
                .onFailure { error -> sendEvent(WeatherListUIEvent.ProceedLocationFailed(location, error)) }
                .toStateOrNull { it.mapToWeatherListState() }
    }

    fun getWeather() = stateFlow(
            {
                setState { UIState.Loading }
                setState {
                    getCurrentWeather()
                            .toState { it.mapToWeatherListState() }
                }
            },
            { error, failedState -> UIState.Failed(error = error, state = failedState) })
}