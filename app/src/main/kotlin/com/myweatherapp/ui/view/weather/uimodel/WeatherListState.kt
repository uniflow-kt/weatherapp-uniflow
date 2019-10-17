package com.myweatherapp.ui.view.weather.uimodel

import com.myweatherapp.ui.view.weather.adapter.WeatherUIItem
import io.uniflow.core.flow.UIState

data class WeatherListState(
        val location: String,
        val firstDay: WeatherUIItem,
        val lastDays: List<WeatherUIItem>
) : UIState()