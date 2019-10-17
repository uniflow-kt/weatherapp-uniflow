package com.myweatherapp.ui.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.openFragment(containerId: Int, fragment: Fragment, parameters: Parameters = emptyList(), addToBackStack: Boolean = true) {
    val last = supportFragmentManager.fragments.lastOrNull()
    if (last != fragment) {
        commitNewFragment(fragment, parameters, containerId, addToBackStack)
    } else {
        Log.e("FragmentActivity","skip openFragment - already open fragment: $fragment")
    }
}

private fun FragmentActivity.commitNewFragment(fragment: Fragment, parameters: Parameters, containerId: Int, addToBackStack: Boolean) {
    fragment.arguments = Bundle().applyParameters(parameters)

    val transaction = supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)

    if (addToBackStack) {
        transaction.addToBackStack("")
    }

    transaction.commitAllowingStateLoss()
}

fun Fragment.openFragment(containerId: Int, fragment: Fragment, parameters: Parameters = emptyList(), addToBackStack: Boolean = true) {
    activity?.openFragment(containerId, fragment, parameters, addToBackStack)
}

inline fun <reified T : Fragment> FragmentActivity.openFragment(containerId: Int, parameters: Parameters = emptyList(), addToBackStack: Boolean = true) {
    val last = supportFragmentManager.fragments.lastOrNull()
    if (last !is T) {
        val fragment: Fragment = createFragment<T>()
        openFragment(containerId, fragment, parameters, addToBackStack)
    } else {
        Log.e("FragmentActivity","skip openFragment - already open fragment: ${T::class}")
    }
}

inline fun <reified T : Fragment> Fragment.openFragment(containerId: Int, parameters: Parameters = emptyList(), addToBackStack: Boolean = true) {
    activity?.openFragment<T>(containerId, parameters, addToBackStack)
}

inline fun <reified T : Fragment> createFragment(): Fragment {
    val fragmentClass = T::class
    return fragmentClass.java.constructors.firstOrNull()?.newInstance() as? Fragment
            ?: error("can't create fragment $fragmentClass")
}

