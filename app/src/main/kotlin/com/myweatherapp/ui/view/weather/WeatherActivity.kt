package com.myweatherapp.ui.view.weather

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.myweatherapp.R
import com.myweatherapp.ui.navigation.navigateTo
import com.myweatherapp.ui.view.weather.uimodel.WeatherListState
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.UIState
import kotlinx.android.synthetic.main.activity_result.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Weather Result View
 */
class WeatherActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    private val viewModel: WeatherListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val weatherTitleFragment = WeatherHeaderFragment()
        val resultListFragment = WeatherListFragment()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.weather_title, weatherTitleFragment)
                .replace(R.id.weather_list, resultListFragment)
                .commit()


        onStates(viewModel) { state ->
            when (state) {
                is WeatherListState -> showLoaded()
                is UIState.Loading -> showEmpty()
                is UIState.Failed -> showError(state.error)
            }
        }
        viewModel.getWeather()
    }

    private fun showLoaded() {
        weather_views.visibility = View.VISIBLE
        weather_error.visibility = View.GONE
    }

    private fun showEmpty() {
        weather_views.visibility = View.GONE
        weather_error.visibility = View.GONE
    }

    private fun showError(error: Throwable?) {
        Log.e(TAG, "error $error while displaying weather")
        weather_views.visibility = View.GONE
        weather_error.visibility = View.VISIBLE
        Snackbar.make(
                weather_result,
                "WeatherActivity got error : $error",
                Snackbar.LENGTH_INDEFINITE
        )
                .setAction(R.string.retry) {
                    navigateTo<WeatherActivity>(isRoot = true)
                }
                .show()
    }
}
