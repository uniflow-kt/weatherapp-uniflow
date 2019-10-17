package com.myweatherapp.data.datasource.webservice.weather

/**
 * Extract Location from Geocode
 */
fun Geocode.mapToLocation(): Location? = results.firstOrNull()?.geometry?.location