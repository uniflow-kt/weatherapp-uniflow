package com.myweatherapp.data.datasource.webservice.weather

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Weather datasource - Retrofit tagged
 */
interface WeatherDataSource {

    @GET("/geocode")
    @Headers("Content-type: application/json")
    fun geocode(@Query("location") address: String): Deferred<Geocode>

    @GET("/weather")
    @Headers("Content-type: application/json")
    fun weather(@Query("lat") lat: Double?, @Query("lon") lon: Double?, @Query("lang") lang: String): Deferred<Weather>

}
