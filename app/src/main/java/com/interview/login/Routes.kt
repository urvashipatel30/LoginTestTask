package com.interview.login

sealed class Routes(val route: String) {
    object Login : Routes("Login")
}