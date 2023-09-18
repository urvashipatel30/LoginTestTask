package com.interview.login.data.remote.login.response

data class LoginResponse(
    val errorCode: String,
    val errorMessage: String,
    val data: UserData?
)