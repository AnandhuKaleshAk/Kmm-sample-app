package org.example.project.base

import app.cash.turbine.test
import common.base.TestData
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkStatic
import kmm_sample_project.composeapp.generated.resources.Res
import kmm_sample_project.composeapp.generated.resources.error_invalid_credentials
import kotlinx.coroutines.test.runTest

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString


class BaseRepositoryTest {

    private lateinit var client: HttpClient
    private lateinit var repository: BaseRepository

    @BeforeTest
    fun setUp() {
        repository = object : BaseRepository() {
        }
    }

    @Test
    fun `safeApiCall return success when API call is successful`() = runTest {
        // Mock Engine with a successful response
        val mockEngine = MockEngine { _ ->
            respond(
                content = """{"testData": "test"}""",  // Simulate JSON response
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val testData = TestData("test")
        client = HttpClient(mockEngine)
        val mockApiCall = suspend {
            val result = client.get {
                url("https://dummyjson.com/products?limit=100&skip=5")
                contentType(ContentType.Application.Json)

            }
            result to testData
        }
        val result = repository.safeApiCall {
            mockApiCall()
        }
        result.test {
            assertEquals(ApiState.Loading, awaitItem())
            assertEquals(ApiState.Success(testData), awaitItem())
        }

    }

    @Test
    fun `safeApiCall return failure when API call is failure`() = runTest {
        val mockEngine = MockEngine { _ ->
            respond(
                content = """{"error": "Invalid user"}""",  // Simulate JSON response
                status = HttpStatusCode.BadRequest,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val testData = TestData(null)

        client = HttpClient(mockEngine)
        val mockApiCall = suspend {
            val result = client.get {
                url("https://dummyjson.com/products?limit=100&skip=5")
                contentType(ContentType.Application.Json)

            }
            result to testData
        }

        val result: Flow<ApiState<TestData?>> = repository.safeApiCall {
            mockApiCall()
        }

        result.test {
            assertEquals(ApiState.Loading, awaitItem())
            assertEquals<ApiState<Any?>>(ApiState.Failure(Error("Invalid user")), awaitItem())
        }
    }

    @Test
    fun `safeApiCall returns failure when API call returns 401`() = runTest {
        // Mock the getString function to return a specific error message
        mockkStatic("org.jetbrains.compose.resources.StringsKt")
        coEvery { getString(Res.string.error_invalid_credentials) } returns "Invalid credentials"

        // Mock Engine with a 401 response
        val mockEngine = MockEngine { _ ->
            respond(
                content = """{"error": "Unauthorized"}""",
                status = HttpStatusCode.Unauthorized,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        client = HttpClient(mockEngine)
        val mockApiCall = suspend {
            val result = client.get {
                url("https://dummyjson.com/products?limit=100&skip=5")
                contentType(ContentType.Application.Json)
            }
            result to null
        }

        val result: Flow<ApiState<Any?>> = repository.safeApiCall {
            mockApiCall()
        }

        result.test {
            assertEquals(ApiState.Loading, awaitItem())
            assertEquals(ApiState.Failure(Error("Invalid credentials")), awaitItem())
        }
    }



}