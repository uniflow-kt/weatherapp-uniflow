package com.myweatherapp.ui.view.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.myweatherapp.R
import com.myweatherapp.ui.view.navigateToDetailActivity
import com.myweatherapp.ui.view.weather.adapter.WeatherListAdapter
import com.myweatherapp.ui.view.weather.adapter.WeatherUIItem
import com.myweatherapp.ui.view.weather.uimodel.WeatherListState
import com.myweatherapp.ui.view.weather.uimodel.WeatherListUIEvent
import io.uniflow.android.livedata.onEvents
import io.uniflow.android.livedata.onStates
import kotlinx.android.synthetic.main.fragment_result_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WeatherListFragment : androidx.fragment.app.Fragment() {

    private val viewModel: WeatherListViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareListView()

        onStates(viewModel) { state ->
            when (state) {
                is WeatherListState -> showWeatherItemList(state.lastDays)
            }
        }

        onEvents(viewModel) { event ->
            when (event) { //peek if multi consumer
                is WeatherListUIEvent.ProceedLocation -> showLoadingLocation(event.location)
                is WeatherListUIEvent.ProceedLocationFailed -> showLocationSearchFailed(event.location, event.error)
            }
        }
    }

    private fun showLocationSearchFailed(location: String, error: Throwable?) {
        Log.i(this::class.simpleName, "received WeatherListUIEvent.ProceedLocationFailed")
    }

    private fun showLoadingLocation(location: String) {
        Log.i(this::class.simpleName, "received WeatherListUIEvent.ProceedLocation")
    }

    private fun prepareListView() {
        weatherList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        weatherList.adapter = WeatherListAdapter(
            requireActivity(),
            emptyList(),
            ::onWeatherItemSelected
        )
    }

    private fun onWeatherItemSelected(resultItem: WeatherUIItem) {
        activity?.navigateToDetailActivity(resultItem.id)
    }

    private fun showWeatherItemList(newList: List<WeatherUIItem>) {
        val adapter: WeatherListAdapter = weatherList.adapter as WeatherListAdapter
        adapter.list = newList
        adapter.notifyDataSetChanged()
    }
}