package com.myweatherapp.ui.view.weather.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.joanzapata.iconify.widget.IconTextView
import com.myweatherapp.R
import com.myweatherapp.domain.model.weather.getColorFromCode

class WeatherListAdapter(
        val context: Context,
        var list: List<WeatherUIItem>,
        private val onDetailSelected: (WeatherUIItem) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<WeatherListAdapter.WeatherResultHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherResultHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherResultHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherResultHolder, position: Int) {
        holder.display(list[position], context, onDetailSelected)
    }

    override fun getItemCount() = list.size

    inner class WeatherResultHolder(item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item) {
        private val weatherItemLayout = item.findViewById<LinearLayout>(R.id.weatherItemLayout)
        private val weatherItemDay = item.findViewById<TextView>(R.id.weatheItemrDay)
        private val weatherItemIcon = item.findViewById<IconTextView>(R.id.weatherItemIcon)

        fun display(
                weatherUIItem: WeatherUIItem,
                context: Context,
                onClick: (WeatherUIItem) -> Unit
        ) {
            weatherItemLayout.setOnClickListener { onClick(weatherUIItem) }
            weatherItemDay.text = weatherUIItem.day
            weatherItemIcon.text = weatherUIItem.icon
            val color = context.getColorFromCode(weatherUIItem.color)
            weatherItemDay.setTextColor(color)
            weatherItemIcon.setTextColor(color)
        }

    }
}