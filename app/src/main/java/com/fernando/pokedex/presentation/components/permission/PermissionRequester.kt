package com.fernando.pokedex.presentation.components.permission

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class PermissionRequester(
    activity: ComponentActivity,
    private val permission: String,
    onDenied: () -> Unit = {},
    onShowRationale: () -> Unit = {}
) {
    private var onGranted: () -> Unit = {}

    private val permissionContract = ActivityResultContracts.RequestPermission()

    private val launcher = activity.registerForActivityResult(permissionContract) { isGranted ->
        when {
            isGranted -> onGranted()
            activity.shouldShowRequestPermissionRationale(permission) -> onShowRationale()
            else -> onDenied()
        }
    }

    fun runWithPermission(onGranted: () -> Unit) {
        this.onGranted = onGranted
        launcher.launch(permission)
    }

    fun runWithPermission() {
        launcher.launch(permission)
    }
}