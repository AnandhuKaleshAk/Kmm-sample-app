package org.example.project

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

actual fun showToast(message: String) {
    Toast.makeText(ContextHolder.context, message, Toast.LENGTH_SHORT).show()
}

