package com.myweatherapp.di

import com.myweatherapp.data.repository.weather.WeatherEntityRepository
import com.myweatherapp.data.repository.weather.WeatherEntityRepositoryImpl
import com.myweatherapp.domain.model.weather.DailyForecastId
import com.myweatherapp.domain.usecase.weather.GetCurrentWeather
import com.myweatherapp.domain.usecase.weather.GetWeatherDetail
import com.myweatherapp.domain.usecase.weather.GetWeatherForGivenLocation
import com.myweatherapp.domain.usecase.weather.LoadCurrentWeather
import com.myweatherapp.ui.view.detail.DetailViewModel
import com.myweatherapp.ui.view.splash.SplashViewModel
import com.myweatherapp.ui.view.weather.WeatherListViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.singleBy

/**
 * App Components
 */
val appModule = module {
    // ViewModels
    viewModel { (id: DailyForecastId) -> DetailViewModel(id, get()) }
    viewModel<SplashViewModel>()
    viewModel<WeatherListViewModel>()

    // Use cases
    factory<GetWeatherDetail>()
    factory<GetWeatherForGivenLocation>()
    factory<GetCurrentWeather>()
    factory<LoadCurrentWeather>()

    // Data Repository
    singleBy<WeatherEntityRepository, WeatherEntityRepositoryImpl>()
}

// Gather all app modules
val onlineWeatherApp = appModule + remoteWebServiceModule
val offlineWeatherApp = onlineWeatherApp + mockWebServiceModule
val offlineDatabaseWeatherApp = offlineWeatherApp + roomDatabaseModule