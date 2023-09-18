package com.interview.login.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.interview.login.domain.LoginService
import com.interview.login.Routes
import com.interview.login.data.remote.login.UserRepository
import com.interview.login.data.local.login.AppDatabase
import com.interview.login.ui.theme.InterviewLoginTheme

class LoginActivity : ComponentActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = UserRepository(LoginService.create())
        appDatabase = AppDatabase.getInstance(applicationContext)

        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(repository)
        ).get(LoginViewModel::class.java)

        setContent {
            InterviewLoginTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Box(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Login.route
                    ) {
                        composable(Routes.Login.route) {
                            LoginScreen(navController, viewModel, appDatabase)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InterviewLoginTheme {
        Greeting("Android")
    }
}