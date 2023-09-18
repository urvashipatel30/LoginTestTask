package com.interview.login.ui.login

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.interview.login.data.local.login.AppDatabase
import com.interview.login.ui.login.entity.UserDataEntity
import com.interview.login.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel,
    appDatabase: AppDatabase
) {
    val context = LocalContext.current
    val userDataDao = appDatabase.userDataDao()

    Row(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val username = remember { mutableStateOf(TextFieldValue()) }
            val password = remember { mutableStateOf(TextFieldValue()) }

            Text(
                text = "JetDevs",
                style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Default)
            )

            Spacer(modifier = Modifier.height(40.dp))
            OutlinedTextField(
                placeholder = { Text(text = "Username") },
                value = username.value,
                textStyle = TextStyle(color = Color.Black),
                onValueChange = { username.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                placeholder = { Text(text = "Password") },
                value = password.value,
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(color = Color.Black),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password.value = it })

            Spacer(modifier = Modifier.height(100.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        viewModel.loginUser(
                            username.value.text,
                            password.value.text
                        ) { success, message, response ->
                            if (success) {
                                // Login successful

                                CoroutineScope(Dispatchers.IO).launch {
                                    val responseData = response?.body()!!

                                    val userDataEntity = UserDataEntity(
                                        userId = responseData.data!!.userId,
                                        userName = responseData.data.userName,
                                        isDeleted = responseData.data.isDeleted,
                                        xAcc = response.headers().get("X-Acc") ?: ""
                                    )
                                    userDataDao.insertUserData(userDataEntity)

                                    withContext(Dispatchers.Main) {
                                        context.startActivity(
                                            Intent(context, MainActivity::class.java).putExtra(
                                                "user",
                                                responseData.data.userName
                                            )
                                        )
                                        (context as Activity).finish()
                                    }
                                }

                            } else {
                                // Login failed, show an error message
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Login")
                }
            }
        }
    }
}