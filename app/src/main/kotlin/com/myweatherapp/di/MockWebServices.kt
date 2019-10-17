package com.myweatherapp.di

import com.myweatherapp.common.file.AndroidReader
import com.myweatherapp.common.file.JsonReader
import com.myweatherapp.data.datasource.webservice.mock.MockInterceptor
import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.koin.experimental.builder.singleBy

/**
 * Local Json Files Datasource
 */
val mockWebServiceModule = module(override = true) {
    // provided web components
    single { createOkHttpClient(get()) }

    single<MockInterceptor>()

    singleBy<JsonReader, AndroidReader>()
}