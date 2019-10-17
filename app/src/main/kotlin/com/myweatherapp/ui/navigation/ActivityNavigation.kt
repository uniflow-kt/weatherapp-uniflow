package com.myweatherapp.ui.navigation

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

inline fun <reified T : Activity> Activity.navigateTo(parameters: Parameters = listOf(), isRoot: Boolean = false) {
    val intent = intentFor<T>()
    intent.apply {
        applyParameters(parameters)
    }
    val callWithFlag = if (isRoot) intent.clearTop().clearTask().newTask() else intent
    startActivity(callWithFlag)
}

inline fun <reified T : Activity> Activity.navigateTo(isRoot: Boolean = false) {
    val intent = intentFor<T>()
    val callWithFlag = if (isRoot) intent.clearTop().clearTask().newTask() else intent
    startActivity(callWithFlag)
}

inline fun <reified T : Activity> Activity.navigateWithResult(
        requestCode: Int,
        parameters: Parameters = listOf(),
        isRoot: Boolean = false
) {
    val intent = intentFor<T>()
    intent.apply {
        applyParameters(parameters)
    }
    val callWithFlag = if (isRoot) intent.clearTop().clearTask().newTask() else intent
    startActivityForResult(callWithFlag, requestCode)
}

inline fun <reified T : Activity> Activity.navigateWithResult(
        requestCode: Int,
        isRoot: Boolean = false
) {
    val intent = intentFor<T>()
    val callWithFlag = if (isRoot) intent.clearTop().clearTask().newTask() else intent
    startActivityForResult(callWithFlag, requestCode)
}


inline fun <reified T : AppCompatActivity> Fragment.navigateTo(parameters: Parameters = listOf(), isRoot: Boolean = false) {
    activity?.navigateTo<T>(parameters, isRoot) ?: error("parent activity is null")
}

inline fun <reified T : AppCompatActivity> Fragment.navigateWithResult(requestCode: Int,
                                                                       parameters: Parameters = listOf(),
                                                                       isRoot: Boolean = false) {
    activity?.navigateWithResult<T>(requestCode, parameters, isRoot)
            ?: error("parent activity is null")
}


