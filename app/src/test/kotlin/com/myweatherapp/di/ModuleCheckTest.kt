package com.myweatherapp.di

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myweatherapp.domain.model.weather.DailyForecastId
import com.myweatherapp.ui.view.detail.DetailViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.android.ext.koin.androidContext
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import org.mockito.Mockito.mock

/**
 * Test Koin modules
 */
@Category(CheckModuleTest::class)
class ModuleCheckTest : KoinTest {

    // Because we have some init states
    @get:Rule
    val rule = InstantTaskExecutorRule()

    val mockedAndroidContext = mock(Application::class.java)

    val fakeUrl = "http://fake.com"
    val fakeId = "42"

    @Test
    fun testRemoteConfiguration() {
        checkModules(parameters = {
            create<DetailViewModel> { parametersOf(DailyForecastId(fakeId)) }
        }) {
            printLogger()
            modules(onlineWeatherApp)
            properties(hashMapOf(Properties.SERVER_URL to fakeUrl))
        }
    }

    @Test
    fun testLocalConfiguration() {
        checkModules(parameters = {
            create<DetailViewModel> { parametersOf(DailyForecastId(fakeId)) }
        }) {
            printLogger()
            androidContext(mockedAndroidContext)
            modules(offlineWeatherApp)
            properties(hashMapOf(Properties.SERVER_URL to fakeUrl))
        }
    }

    @Test
    fun testTestConfiguration() {
        checkModules(parameters = {
            create<DetailViewModel> { parametersOf(DailyForecastId(fakeId)) }
        }) {
            printLogger()
            androidContext(mockedAndroidContext)
            modules(testWeatherApp)
            properties(hashMapOf(Properties.SERVER_URL to fakeUrl))
        }
    }
}