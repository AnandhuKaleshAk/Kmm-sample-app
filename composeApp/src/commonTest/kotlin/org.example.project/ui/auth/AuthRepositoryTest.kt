package org.example.project.ui.auth


import app.cash.turbine.test
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.every
import org.example.project.network.ApiService
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.example.project.base.ApiState
import org.example.project.base.Error
import org.example.project.data.model.LoginResponse
import org.example.project.utils.getOrAwaitValues
import kotlin.test.Test
import kotlin.test.assertEquals


class AuthRepositoryTest {

    // Mock the ApiService
    private val mockApiService: ApiService = mockk()

    // Create an instance of AuthRepository with the mocked ApiService
    private val authRepository = AuthRepository(mockApiService)

    @Test
    fun `login should return pair of http response and LoginResponse on success`() = runBlocking {
        val email = "eve.holt@reqres.in"
        val password = "cityslicka"

        // Mock the HttpResponse and LoginResponse
        val mockHttpResponse: HttpResponse = mockk(relaxed = true)
        val mockLoginResponse: LoginResponse = mockk(relaxed = true)

        every { mockHttpResponse.status } returns HttpStatusCode.OK
        coEvery { mockHttpResponse.body<LoginResponse>() } returns mockLoginResponse

        coEvery { mockApiService.login(email, password) } returns mockHttpResponse

        val results = authRepository.login(email, password).getOrAwaitValues()
        assertEquals(
            listOf(ApiState.Loading, ApiState.Success(mockLoginResponse)),
            results
        )
    }


}

