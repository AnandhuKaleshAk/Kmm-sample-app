package org.example.project.utils

import kotlinx.coroutines.flow.*

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeout

/**
 * Collects all emitted values from a Flow within the given timeout.
 * Works across Android (JVM) and iOS (Native).
 */
suspend fun <T> Flow<T>.getOrAwaitValues(timeoutMillis: Long = 2000): List<T> {
    val resultList = mutableListOf<T>()
    withTimeout(timeoutMillis) {
        collect { resultList.add(it) } // Collects all values
    }
    return resultList
}
