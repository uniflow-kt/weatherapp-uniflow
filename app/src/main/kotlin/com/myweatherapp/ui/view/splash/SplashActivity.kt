package com.myweatherapp.ui.view.splash

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.myweatherapp.R
import com.myweatherapp.ui.navigation.navigateTo
import com.myweatherapp.ui.view.weather.WeatherActivity
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.UIState
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Search Weather View
 */
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        onStates(splashViewModel) { state ->
            when (state) {
                is UIState.Loading -> showIsLoading()
                is UIState.Success -> showIsLoaded()
                is UIState.Failed -> showError(state.error)
            }
        }
        splashViewModel.getLastWeather()
    }

    private fun showIsLoading() {
        val animation =
                AnimationUtils.loadAnimation(applicationContext, R.anim.infinite_blinking_animation)
        splashIcon.startAnimation(animation)
    }

    private fun showIsLoaded() {
        navigateTo<WeatherActivity>(isRoot = true)
    }

    private fun showError(error: Throwable?) {
        splashIcon.visibility = View.GONE
        splashIconFail.visibility = View.VISIBLE
        Snackbar.make(splash, "SplashActivity got error : $error", Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) {
                    splashViewModel.getLastWeather()
                }
                .show()
    }
}
