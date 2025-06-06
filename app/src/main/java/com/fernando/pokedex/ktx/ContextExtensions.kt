package com.fernando.pokedex.ktx

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat

@SuppressLint("MissingPermission")
fun Context.isInternetAvailable(): Boolean {
    val connectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    connectivityManager?.let {
        it.getNetworkCapabilities(it.activeNetwork)?.apply {
            if (hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_VALIDATED
                )
            ) {
                return when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
    }
    return false
}

fun Context.hasPermissions(vararg permissions: String): Boolean {
    permissions.forEach { permission ->
        if (!hasPermission(permission)) {
            return false
        }
    }

    return true
}

@SuppressLint("MissingPermission")
fun Context.vibratePhone(durationMillis: Long = 500L) {
    val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(durationMillis)
    }
}

fun Context.hasPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED