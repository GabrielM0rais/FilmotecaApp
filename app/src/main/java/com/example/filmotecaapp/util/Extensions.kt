package com.example.filmotecaapp.util

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.example.filmotecaapp.R

fun Fragment.hideKeyboard() {
    val view = activity?.currentFocus
    if (view != null) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}

fun <T : Parcelable> Intent.getParcelableCompat(key: String, clazz: Class<T>): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableExtra(key, clazz)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

fun <T : Parcelable?> Bundle.getParcelableCompat(key: String, clazz: Class<T>): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, clazz)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

fun Bundle.getStringCompat(key: String): String? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> getString(key)
    else -> @Suppress("DEPRECATION") get(key) as? String
}

private val navOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()

fun NavController.navigateWithAnimations(destinationId: Int) {
    this.navigate(destinationId, null, navOptions)
}

fun NavController.navigateWithAnimations(directions: NavDirections) {
    this.navigate(directions, navOptions)
}