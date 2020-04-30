package com.myweatherapp.ui.view.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.myweatherapp.R
import com.myweatherapp.ui.view.navigateToDetailActivity
import com.myweatherapp.ui.view.weather.adapter.WeatherListAdapter
import com.myweatherapp.ui.view.weather.adapter.WeatherUIItem
import com.myweatherapp.ui.view.weather.uimodel.WeatherListState
import io.uniflow.androidx.flow.onStates
import kotlinx.android.synthetic.main.fragment_result_list.weatherList
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WeatherListFragment : Fragment(R.layout.fragment_result_list) {

    private val viewModel: WeatherListViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareListView()

        onStates(viewModel) { state ->
            when (state) {
                is WeatherListState -> showWeatherItemList(state.lastDays)
            }
        }
    }

    private fun prepareListView() {
        weatherList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        weatherList.adapter = WeatherListAdapter(
                activity!!,
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