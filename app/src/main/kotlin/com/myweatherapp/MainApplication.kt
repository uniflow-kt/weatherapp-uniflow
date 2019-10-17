package com.myweatherapp

import android.app.Application
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.WeathericonsModule
import com.myweatherapp.di.offlineDatabaseWeatherApp
import io.uniflow.androidx.logger.AndroidMessageLogger
import io.uniflow.core.logger.UniFlowLogger
import io.uniflow.core.logger.UniFlowLogger.FUN_TAG
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Main Application
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@MainApplication)
            androidFileProperties()
            modules(offlineDatabaseWeatherApp)
        }

        UniFlowLogger.init(AndroidMessageLogger(FUN_TAG, true))

        Iconify.with(WeathericonsModule())
    }
}
