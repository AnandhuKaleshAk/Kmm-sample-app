package org.example.project


import android.app.Application

// Initialize this context in your Android Application class:
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ContextHolder.context = this
    }
}