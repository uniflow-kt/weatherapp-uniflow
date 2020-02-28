package com.myweatherapp.ui.view.weather

import com.myweatherapp.domain.usecase.weather.GetCurrentWeather
import com.myweatherapp.domain.usecase.weather.GetWeatherForGivenLocation
import com.myweatherapp.ui.view.weather.uimodel.WeatherListState
import com.myweatherapp.ui.view.weather.uimodel.WeatherListUIEvent
import com.myweatherapp.ui.view.weather.uimodel.mapToWeatherListState
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.actionOn
import io.uniflow.core.flow.data.UIState

class WeatherListViewModel(
    private val getCurrentWeather: GetCurrentWeather,
    private val getWeatherForLocation: GetWeatherForGivenLocation
) : AndroidDataFlow() {

    fun loadNewLocation(location: String) = actionOn<WeatherListState>(
        onAction = {
            sendEvent(WeatherListUIEvent.ProceedLocation(location))
            val forecast = getWeatherForLocation(location)
            setState { forecast.mapToWeatherListState() }
        },
        onError = { error, _ -> sendEvent(WeatherListUIEvent.ProceedLocationFailed(location, error)) })

    fun getWeather() = action(
        onAction = {
            val forecast = getCurrentWeather()
            setState { forecast.mapToWeatherListState() }
        },
        onError = { error, _ -> setState { UIState.Failed("getWeather failed", error) } })
}