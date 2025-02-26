package org.example.project

import platform.UIKit.UIDevice
import kmm_sample_project.composeapp.generated.resources.Res
import kmm_sample_project.composeapp.generated.resources.error_invalid_credentials
import kmm_sample_project.composeapp.generated.resources.error_network
import kmm_sample_project.composeapp.generated.resources.error_unexpected
import org.jetbrains.compose.resources.getString


class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual suspend fun getPlatformString(resource: String): String {
    return when (resource) {
        "error_invalid_credentials" -> getString(Res.string.error_invalid_credentials)
        "error_network" -> getString(Res.string.error_network)
        "error_unexpected" -> getString(Res.string.error_unexpected)
        else -> "Unknown error"
    }
}