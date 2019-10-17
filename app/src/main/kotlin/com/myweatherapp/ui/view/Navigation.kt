package com.myweatherapp.ui.view

import android.app.Activity
import com.myweatherapp.ui.navigation.NamedParameter
import com.myweatherapp.ui.navigation.navigateTo
import com.myweatherapp.ui.view.detail.DetailActivity


fun Activity.navigateToDetailActivity(itemId: String) {
    navigateTo<DetailActivity>(parameters = listOf(Arguments.WEATHER_DETAIL_ID to itemId))
}

enum class Arguments : NamedParameter {
    WEATHER_DETAIL_ID
}