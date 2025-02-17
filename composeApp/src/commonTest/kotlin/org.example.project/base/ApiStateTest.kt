package org.example.project.base

import io.mockk.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class ApiStateTest {


    @Test
    fun `doOnSuccess should invoke action when ApiState is Success`() = runTest {
        val successData = "Success Data"
        val action: suspend (String) -> Unit = mockk(relaxed = true)
        val flow = flowOf(ApiState.Success(successData)).doOnSuccess(action)

        flow.collect()

        coVerify { action(successData) }
    }

    @Test
    fun `doOnFailure should invoke action when ApiState is Failure`() = runTest {
        val error = Error("Some error")
        val action: suspend (Error?) -> Unit = mockk(relaxed = true)
        val flow = flowOf(ApiState.Failure(error)).doOnFailure(action)

        flow.collect()

        coVerify { action(error) }
    }


    @Test
    fun `doOnLoading should invoke action when ApiState is Loading`() = runTest {
        val action: suspend () -> Unit = mockk(relaxed = true)
        val flow = flowOf(ApiState.Loading).doOnLoading(action)

        flow.collect()

        coVerify { action() }
    }


}
