package org.example.project.ui.auth

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.example.project.MainActivity
import org.example.project.data.model.LoginResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: LoginViewModel
    private val loginState = MutableStateFlow<LoginViewModel.LoginScreenState>(LoginViewModel.LoginScreenState.Loading)

    @Before
    fun setUp() {
        viewModel = mockk(relaxed = true) {
            loginViewState = loginState
        }
    }

    @Test
    fun loginScreen_displaysEmailAndPasswordFields() {
        // Enter email and password
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("123456")

        // Click login button
        composeTestRule.onNodeWithTag("loginButton").performClick()
    }

    @Test
    fun loginScreen_emptyEmailAndPasswordFields() {
        // Ensure login button is not clickable when fields are empty
        composeTestRule.onNodeWithTag("loginButton").assertDoesNotExist()
    }

    @Test
    fun loginScreen_invalidEmailFormat() {
        // Enter invalid email and password
        composeTestRule.onNodeWithText("Email").performTextInput("invalid-email")
        composeTestRule.onNodeWithText("Password").performTextInput("123456")

        // Click login button
        composeTestRule.onNodeWithTag("loginButton").performClick()

        // Check for error message or disabled button
        composeTestRule.onNodeWithText("Invalid email format").assertExists()
    }

    @Test
    fun loginScreen_successfulLogin() {
        // Simulate successful login state
        every { viewModel.loginViewState } returns MutableStateFlow(LoginViewModel.LoginScreenState.Success(
            LoginResponse("token")
        ))

        // Enter email and password
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("123456")

        // Click login button
        composeTestRule.onNodeWithTag("loginButton").performClick()

        // Check for success state handling
        composeTestRule.onNodeWithText("Login successful").assertExists()
    }

    @Test
    fun loginScreen_loginError() {
        // Simulate login error state
        every { viewModel.loginViewState } returns MutableStateFlow(LoginViewModel.LoginScreenState.Error("Login failed"))

        // Enter email and password
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("123456")

        // Click login button
        composeTestRule.onNodeWithTag("loginButton").performClick()

        // Check for error message
        composeTestRule.onNodeWithText("Login failed").assertExists()
    }
}