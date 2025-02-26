
import app.cash.turbine.test

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.example.project.base.ApiState
import org.example.project.base.Error
import org.example.project.base.doOnFailure
import org.example.project.base.doOnLoading
import org.example.project.base.doOnSuccess


import kotlin.test.assertTrue


class ApiStateTest {

    @Test
    fun `ApiState toString should return correct values`() {
        val cases = listOf(
            ApiState.Success("Data") to "Success(data=Data)",
            ApiState.Failure(Error("Error")) to "Failure(msg=Error(error=Error))",
            ApiState.Loading to "Loading"
        )

        cases.forEach { (state, expectedString) ->
            assertEquals(expectedString, state.toString())
        }
    }


    @Test
    fun `doOnSuccess should execute action on success`() = runTest {
        var successTriggered = false
        val flow = flowOf(ApiState.Success("Hello")).doOnSuccess { successTriggered = true }
        flow.test {
            assertEquals(ApiState.Success("Hello"), awaitItem())
            awaitComplete()
        }
        assertTrue(successTriggered)
    }

    @Test
    fun `doOnFailure should execute action on failure`() = runTest {
        var failureMessage: String? = null
        val flow = flowOf(ApiState.Failure(Error("Error"))).doOnFailure { failureMessage = it?.error }
        flow.test {
            val result = awaitItem()
            assertTrue(result is ApiState.Failure)
            awaitComplete()
        }
        assertEquals("Error", failureMessage)
    }

    @Test
    fun `doOnLoading should execute action on loading`() = runTest {
        var loadingTriggered = false
        val flow = flowOf(ApiState.Loading).doOnLoading { loadingTriggered = true }

        flow.test {
            assertEquals(ApiState.Loading, awaitItem())
            awaitComplete()
        }
        assertTrue(loadingTriggered)
    }
}
