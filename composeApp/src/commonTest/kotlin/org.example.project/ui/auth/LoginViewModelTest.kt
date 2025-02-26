package org.example.project.ui.auth

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.example.project.base.ApiState
import org.example.project.base.ApiState.Failure
import org.example.project.base.Error
import org.example.project.data.model.LoginResponse
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


class LoginViewModelTest{
    private lateinit var viewModel: LoginViewModel
    private  var authRepository: AuthRepository= mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp(){
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel= LoginViewModel((authRepository))

    }

    @Test
    fun `login should update view state to Loading then Success`()= runTest {
        val mockResponse = LoginResponse("mock_token")

            coEvery { authRepository.login(any(), any()) } returns flow {
                emit(ApiState.Loading)
                emit(ApiState.Success(mockResponse))
            }
        viewModel.login("test@example.com", "password")

        viewModel.loginViewState.test {
            assertIs<LoginViewModel.LoginScreenState.Loading>(awaitItem()) // Expect Loading state
            val successState = awaitItem()
            assertIs<LoginViewModel.LoginScreenState.Success>(successState)
            assertEquals("mock_token", successState.responseData.token) // Validate response

        }

    }


    @Test
    fun `login should update view state to Loading then Error with invalid user`() = runTest {
        val mockError = Error("Invalid user")

        coEvery { authRepository.login(any(), any()) } returns flow {
            emit(ApiState.Loading)
            emit(ApiState.Failure(mockError))
        }

        viewModel.login("test@example.com", "password")

        viewModel.loginViewState.test {
            assertIs<LoginViewModel.LoginScreenState.Loading>(awaitItem()) // Expect Loading state
            val errorState = awaitItem()
            assertIs<LoginViewModel.LoginScreenState.Error>(errorState)
            assertEquals("Invalid user", errorState.errorMessage) // Validate error message
        }
    }



}