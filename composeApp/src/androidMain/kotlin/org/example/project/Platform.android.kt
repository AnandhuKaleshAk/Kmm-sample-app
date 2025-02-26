package org.example.project

import android.os.Build
import kmm_sample_project.composeapp.generated.resources.Res
import kmm_sample_project.composeapp.generated.resources.error_invalid_credentials
import kmm_sample_project.composeapp.generated.resources.error_network
import kmm_sample_project.composeapp.generated.resources.error_unexpected
import org.jetbrains.compose.resources.getString

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual suspend fun getPlatformString(resource: String): String {
    return when (resource) {
        "error_invalid_credentials" -> getString(Res.string.error_invalid_credentials)
        "error_network" -> getString(Res.string.error_network)
        "error_unexpected" -> getString(Res.string.error_unexpected)
        else -> "Unknown error"
    }
}