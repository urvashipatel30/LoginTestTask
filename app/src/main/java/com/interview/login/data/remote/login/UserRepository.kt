package com.interview.login.data.remote.login

import com.interview.login.data.remote.login.request.LoginRequest
import com.interview.login.data.remote.login.response.LoginResponse
import retrofit2.Response

class UserRepository(private val loginService: LoginAPI) {

    suspend fun login(username: String, password: String): Response<LoginResponse> {
        val requestBody = LoginRequest(username, password)
        return loginService.login(requestBody)
    }
}