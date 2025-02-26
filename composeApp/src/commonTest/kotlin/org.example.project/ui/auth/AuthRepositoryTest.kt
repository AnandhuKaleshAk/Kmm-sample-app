import app.cash.turbine.test
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.project.base.ApiState
import org.example.project.base.Error
import org.example.project.data.model.LoginResponse
import org.example.project.network.ApiService
import org.example.project.ui.auth.AuthRepository

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class AuthRepositoryTest {

    private lateinit var mockEngine: MockEngine
    private lateinit var httpClient: HttpClient
    private lateinit var apiService: ApiService
    private lateinit var authRepository: AuthRepository


    @Test
    fun `login should return success when API returns 200`() = runTest {
        //success login response should emit Login response

        val loginResponse = LoginResponse("mock_token")
        mockEngine = MockEngine { _ ->
            respond(
                content = Json.encodeToString(loginResponse),  // Simulate JSON response
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
        apiService = ApiService(httpClient)
        authRepository = AuthRepository(apiService)
        val result = authRepository.login("test@example.com", "password")

        result.test {
            assertIs<ApiState.Loading>(awaitItem()) // First, expect Loading
            val successState = awaitItem()
            assertIs<ApiState.Success<LoginResponse>>(successState)
            assertEquals("mock_token", successState.data.token) // Check token
            awaitComplete()
        }
    }

    @Test
    fun `login should return error when API returns 400`()= runTest {
      //mock invalid username and password
      val  mockEngine = MockEngine { _ ->
            respond(
                content = """{"error": "Invalid user"}""",  // Simulate JSON response
                status = HttpStatusCode.BadRequest,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
        apiService = ApiService(httpClient)
        authRepository = AuthRepository(apiService)
        val result = authRepository.login("test@example.com", "password")

        result.test {
            assertEquals(ApiState.Loading, awaitItem())
            assertEquals<ApiState<Any?>>(ApiState.Failure(Error("Invalid user")), awaitItem())
            awaitComplete()
        }

    }
}
