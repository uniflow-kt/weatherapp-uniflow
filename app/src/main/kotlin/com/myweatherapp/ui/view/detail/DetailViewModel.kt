package com.myweatherapp.ui.view.detail

import com.myweatherapp.domain.model.weather.DailyForecastId
import com.myweatherapp.domain.usecase.weather.GetWeatherDetail
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState

class DetailViewModel(
        private val id: DailyForecastId,
        private val getWeatherDetail: GetWeatherDetail
) : AndroidDataFlow() {

    fun getDetail() = action(
            onAction = {
                val dailyForecast = getWeatherDetail(id)
                setState { dailyForecast.mapToDetailState() }
            },
            onError = { error, _ -> setState { UIState.Failed("getDetail failed", error) } })
}