package com.interview.login.data.remote.login

import com.interview.login.data.remote.login.request.LoginRequest
import com.interview.login.data.remote.login.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginAPI {

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}