package org.example.project

import android.annotation.SuppressLint
import android.content.Context

// To provide the application context, you can create a simple ContextHolder in Android:
@SuppressLint("StaticFieldLeak")
object ContextHolder {
    lateinit var context: Context
}


