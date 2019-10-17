package com.myweatherapp.ui.view.weather.uimodel

import io.uniflow.core.flow.UIEvent

sealed class WeatherListUIEvent : UIEvent() {
    data class ProceedLocation(val location: String) : WeatherListUIEvent()
    data class ProceedLocationFailed(val location: String, val error: Throwable? = null) : WeatherListUIEvent()
}