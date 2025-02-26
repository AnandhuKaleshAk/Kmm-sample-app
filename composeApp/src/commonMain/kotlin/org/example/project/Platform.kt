package org.example.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


expect suspend fun getPlatformString(resource: String): String
