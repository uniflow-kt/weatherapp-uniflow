package com.myweatherapp.ui.view.detail

import com.myweatherapp.domain.model.weather.DailyForecastId
import com.myweatherapp.domain.usecase.weather.GetWeatherDetail
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.UIState
import io.uniflow.core.result.toState

class DetailViewModel(private val id: DailyForecastId, private val getWeatherDetail: GetWeatherDetail) : AndroidDataFlow() {

    init {
        setState { UIState.Empty }
    }

    fun getDetail() = stateFlow(
            {
                setState { UIState.Loading }
                setState {
                    getWeatherDetail(id)
                            .toState { it.mapToDetailState() }
                }
            },
            { error, _ -> UIState.Failed(error = error) })

}