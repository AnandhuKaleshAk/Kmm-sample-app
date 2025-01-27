package org.example.project.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(@SerialName("email") val email:String, @SerialName("password") val password:String)