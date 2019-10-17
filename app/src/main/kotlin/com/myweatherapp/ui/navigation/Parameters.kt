@file:Suppress("UNCHECKED_CAST")

package com.myweatherapp.ui.navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.myweatherapp.common.json.fromJson
import java.io.Serializable


typealias Parameter<T> = Pair<NamedParameter, T>
typealias Parameters = List<Parameter<*>>

interface NamedParameter {
    fun parameterName() = this.toString().toLowerCase()
}

/**
 * Retrieve param from Activity intent
 */
fun <T : Any> Activity.param(key: NamedParameter) = lazy { getParam(key) as T }

fun <T : Any> Activity.getParam(key: NamedParameter) = intent?.extras?.get(key.parameterName()) as? T
        ?: throw MissingArgumentException("Intent extra '$key' is missing")

fun <T : Any> Fragment.param(key: NamedParameter) = lazy { getParam(key) as T }

fun <T : Any> Fragment.getParam(key: NamedParameter) = arguments?.get(key.parameterName()) as? T
        ?: throw MissingArgumentException("Bundle '$key' is missing")

inline fun <reified T : Any> Fragment.getJsonParam(key: NamedParameter) = getParam<String>(key).fromJson<T>()

inline fun <reified T : Any> Fragment.jsonParam(key: NamedParameter) = lazy { getJsonParam<T>(key) }

class MissingArgumentException(msg: String) : Exception(msg)


fun Intent.applyParameters(parameters: Parameters) {
    parameters.forEach { (k, value) ->
        val key = k.parameterName()
        when (value) {
            is Int -> putExtra(key, value)
            is Long -> putExtra(key, value)
            is String -> putExtra(key, value)
            is Parcelable -> putExtra(key, value)
            is Serializable -> putExtra(key, value)
            else -> error("can't apply extra $k - unknown type")
        }
    }
}

fun Bundle.applyParameters(parameters: Parameters): Bundle {
    parameters.forEach { (k, value) ->
        val key = k.parameterName()
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Parcelable -> putParcelable(key, value)
            is Serializable -> putSerializable(key, value)
            else -> error("can't apply extra $key - unknown type")
        }
    }

    return this
}