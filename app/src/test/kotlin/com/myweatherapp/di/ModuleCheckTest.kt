package com.myweatherapp.di

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myweatherapp.domain.model.weather.DailyForecastId
import com.myweatherapp.ui.view.detail.DetailViewModel
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mockito.mock

/**
 * Test Koin modules
 */
class ModuleCheckTest : KoinTest {

    // Because we have some init states
    @get:Rule
    val rule = InstantTaskExecutorRule()

    val mockedAndroidContext = mock(Application::class.java)

    val fakeUrl = "http://fake.com"
    val fakeId = "42"

    @Test
    fun testRemoteConfiguration() {
        koinApplication {
            printLogger()
            modules(onlineWeatherApp)
            properties(hashMapOf(Properties.SERVER_URL to fakeUrl))
        }.checkModules {
            create<DetailViewModel> { parametersOf(DailyForecastId(fakeId)) }
        }
    }

    @Test
    fun testLocalConfiguration() {
        koinApplication {
            printLogger()
            androidContext(mockedAndroidContext)
            modules(offlineWeatherApp)
            properties(hashMapOf(Properties.SERVER_URL to fakeUrl))
        }.checkModules {
            create<DetailViewModel> { parametersOf(DailyForecastId(fakeId)) }
        }
    }

    @Test
    fun testTestConfiguration() {
        koinApplication {
            printLogger()
            androidContext(mockedAndroidContext)
            modules(testWeatherApp)
            properties(hashMapOf(Properties.SERVER_URL to fakeUrl))
        }.checkModules {
            create<DetailViewModel> { parametersOf(DailyForecastId(fakeId)) }
        }
    }
}