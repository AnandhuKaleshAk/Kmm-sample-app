package org.example.project.network

import org.example.project.data.model.LoginRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.utils.APIUrls

class ApiService(private val httpClient: HttpClient) {

    suspend fun login(email: String, password: String): HttpResponse {
        val loginRequest = LoginRequest(email, password)
        return httpClient.post {
            url("${APIUrls.BASE_URL}/login")
            contentType(ContentType.Application.Json)
            setBody(loginRequest)

        }.body()
    }
}