package org.example.project.base

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kmm_sample_project.composeapp.generated.resources.Res
import kmm_sample_project.composeapp.generated.resources.error_invalid_credentials
import kotlinx.coroutines.test.runTest
import org.example.project.utils.getOrAwaitValues
import org.jetbrains.compose.resources.getString
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class BaseRepositoryTest {

    private val repository = object : BaseRepository() {} // Create an anonymous subclass to test


    @Test
    fun `safeApiCall should emit Success when API call is successful`() = runTest {
        // Arrange
        val mockResponse = mockk<HttpResponse>()
        val mockData = "Mock Data"
        val mockApiCall: suspend () -> Pair<HttpResponse, String> = { Pair(mockResponse, mockData) }

        coEvery { mockResponse.status } returns HttpStatusCode.OK
        coEvery { mockResponse.bodyAsText() } returns ""

        // Act
        val results = repository.safeApiCall(mockApiCall).getOrAwaitValues()

        // Assert
        assertEquals(
            listOf(ApiState.Loading, ApiState.Success(mockData)),
            results
        )
    }



}