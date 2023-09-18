package com.interview.login.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.login.data.remote.login.UserRepository
import com.interview.login.data.remote.login.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun loginUser(
        username: String,
        password: String,
        onResult: (Boolean, String, response: Response<LoginResponse>?) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.login(username, password)

            if (response.isSuccessful && response.body() != null) {
                val responseData = response.body()!!
                onResult(true, responseData.errorMessage, response)
            } else {
                onResult(false, "Login failed", null)
            }
        }
    }
}