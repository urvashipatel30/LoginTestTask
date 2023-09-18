package com.interview.login.data.remote.login.response

data class UserData(
    val userId: String,
    val userName: String,
    val isDeleted: Boolean
)