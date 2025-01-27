package org.example.project.ui.auth

import org.example.project.data.model.LoginResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import org.example.project.base.BaseRepository
import org.example.project.network.ApiService

class AuthRepository (private  val apiService: ApiService):BaseRepository(){

    suspend fun login(email:String,password:String)=safeApiCall{
        val response: HttpResponse = apiService.login(email, password)
        val loginResponse: LoginResponse = response.body()
        // Return the response as a pair (HttpResponse, LoginResponse)
        Pair(response, loginResponse)
    }

}