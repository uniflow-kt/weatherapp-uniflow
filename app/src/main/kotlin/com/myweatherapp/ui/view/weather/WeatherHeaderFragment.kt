package com.myweatherapp.ui.view.weather

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.myweatherapp.R
import com.myweatherapp.domain.model.weather.getColorFromCode
import com.myweatherapp.ui.android.toast
import com.myweatherapp.ui.view.navigateToDetailActivity
import com.myweatherapp.ui.view.weather.adapter.WeatherUIItem
import com.myweatherapp.ui.view.weather.uimodel.WeatherListState
import com.myweatherapp.ui.view.weather.uimodel.WeatherListUIEvent
import io.uniflow.android.livedata.onEvents
import io.uniflow.android.livedata.onStates
import kotlinx.android.synthetic.main.fragment_result_header.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WeatherHeaderFragment : androidx.fragment.app.Fragment() {

    private val viewModel: WeatherListViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_result_header, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onStates(viewModel) { state ->
            when (state) {
                is WeatherListState -> showWeather(state.location, state.firstDay)
            }
        }

        onEvents(viewModel) { event ->
            when (event) { //peek if multi consumer
                is WeatherListUIEvent.ProceedLocation -> showLoadingLocation(event.location)
                is WeatherListUIEvent.ProceedLocationFailed -> showLocationSearchFailed(event.location, event.error)
            }
        }
    }

    private fun showWeather(location: String, weather: WeatherUIItem) {
        weatherCity.text = location
        weatherIcon.text = weather.icon
        weatherDay.text = weather.day
        weatherTempText.text = weather.temperature
        weatherText.text = weather.shortText
        val color = requireContext().getColorFromCode(weather.color)
        weatherHeader.background.setTint(color)

        weatherCityCard.setOnClickListener {
            promptLocationDialog()
        }
        weatherHeader.setOnClickListener {
            activity?.navigateToDetailActivity(weather.id)
        }
    }

    private fun promptLocationDialog() {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(getString(R.string.enter_location))
        val editText = EditText(context)
        editText.hint = getString(R.string.location_hint)
        editText.maxLines = 1
        editText.inputType = InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS
        dialog.setView(editText)
        dialog.setPositiveButton(getString(R.string.search)) { dialogInterface, _ ->
            dialogInterface.dismiss()
            val newLocation = editText.text.trim().toString().toLowerCase()
            viewModel.loadNewLocation(newLocation)
        }
        dialog.setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        dialog.show()
    }

    private fun showLoadingLocation(location: String) {
        Log.i(this::class.simpleName, "received WeatherListUIEvent.ProceedLocation")
        activity?.toast(getString(R.string.loading_location) + " $location ...", Toast.LENGTH_SHORT)
    }

    private fun showLocationSearchFailed(location: String, error: Throwable?) {
        Log.i(this::class.simpleName, "received WeatherListUIEvent.ProceedLocationFailed")
        System.err.println("showLocationSearchFailed: $error")
        Snackbar.make(weatherHeader, getString(R.string.loading_error), Snackbar.LENGTH_LONG)
            .setAction(R.string.retry) {
                viewModel.loadNewLocation(location)
            }
            .show()
    }
}