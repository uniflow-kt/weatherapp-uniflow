package com.myweatherapp.common.json

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

inline fun <reified T : Any> String.fromJson(): T {
    val kClass = T::class.java
    return fromJsonOrNull() ?: error("can't parse '$this' for $kClass")
}

inline fun <reified T : Any> String.fromJsonOrNull(): T? {
    val kClass = T::class.java
    return try {
        moshi.adapter(kClass).fromJson(this)
    } catch (e: Exception) {
        Log.e("Moshi", "can't parse '$this' for $kClass - $e")
        null
    }
}

inline fun <reified T : Any> T.toJson(): String = moshi.adapter(T::class.java).toJson(this)