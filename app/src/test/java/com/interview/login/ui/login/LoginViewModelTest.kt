package com.interview.login.ui.login

import com.interview.login.data.remote.login.UserRepository
import com.interview.login.data.remote.login.response.LoginResponse
import com.interview.login.data.remote.login.response.UserData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    // Dispatchers.Main is required for viewModelScope which uses it by default
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `test loginUser with right credential`() {
        // 1. Mock the UserRepository
        val repository = mockk<UserRepository>()
        val viewModel = LoginViewModel(repository)

        // 2. Mock the login response
        val mockResponse = Response.success(
            LoginResponse(
                errorCode = "00", errorMessage = "Sukses.",
                UserData("1111", "username", true)
            )
        )
        coEvery { repository.login("username", "1111111") } returns mockResponse

        // 3. Call the loginUser method and verify the results
        viewModel.loginUser("username", "1111111") { success, message, response ->
            assert(success)
            assert(message == "Sukses.")
            assert(response == mockResponse)
        }
    }

    @Test
    fun `test loginUser with wrong credential`() {
        // 1. Mock the UserRepository
        val repository = mockk<UserRepository>()
        val viewModel = LoginViewModel(repository)

        // 2. Mock the login response
        val mockResponse = Response.success(
            LoginResponse(
                errorCode = "01", errorMessage = "your password is incorrect.",null
            )
        )
        coEvery { repository.login("username", "fake") } returns mockResponse

        // 3. Call the loginUser method and verify the results
        viewModel.loginUser("username", "fake") { success, message, response ->
            assert(success)
            assert(message == "your password is incorrect.")
            assert(response == mockResponse)
        }
    }
}
