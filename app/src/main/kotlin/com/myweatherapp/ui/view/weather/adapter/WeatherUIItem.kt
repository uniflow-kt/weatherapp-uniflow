package com.myweatherapp.ui.view.weather.adapter

data class WeatherUIItem(
        val id: String,
        val icon: String,
        val day: String,
        val temperature: String,
        val shortText: String,
        val color: Int
)
