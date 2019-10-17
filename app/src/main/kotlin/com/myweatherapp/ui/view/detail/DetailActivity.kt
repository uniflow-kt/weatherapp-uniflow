package com.myweatherapp.ui.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.myweatherapp.R
import com.myweatherapp.domain.model.weather.DailyForecastId
import com.myweatherapp.domain.model.weather.getColorFromCode
import com.myweatherapp.ui.navigation.param
import com.myweatherapp.ui.view.Arguments
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.UIState
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Weather DetailState View
 */
class DetailActivity : AppCompatActivity() {

    // Get all needed data
    private val detailId: String by param(Arguments.WEATHER_DETAIL_ID)
    private val detailViewModel: DetailViewModel by viewModel { parametersOf(DailyForecastId(detailId)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        onStates(detailViewModel) { state ->
            when (state) {
                //omit Loading & Empty state
                is UIState.Failed -> showError(state.error)
                is DetailState -> showDetail(state)
            }
        }
        detailViewModel.getDetail()
    }

    private fun showError(error: Throwable?) {
        Snackbar.make(
                weatherItem,
                getString(R.string.loading_error) + " - $error",
                Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showDetail(weather: DetailState) {
        weatherIcon.text = weather.icon
        weatherDay.text = weather.day
        weatherText.text = weather.fullText
        weatherWindText.text = weather.wind
        weatherTempText.text = weather.temperature
        weatherHumidityText.text = weather.humidity
        weatherItem.background.setTint(getColorFromCode(weather.color))
        // Set back on background click
        weatherItem.setOnClickListener {
            onBackPressed()
        }
    }
}
