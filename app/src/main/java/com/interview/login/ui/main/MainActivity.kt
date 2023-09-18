package com.interview.login.ui.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.interview.login.ui.theme.InterviewLoginTheme

class MainActivity : ComponentActivity() {
    private lateinit var activity: Activity
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        activity = this@MainActivity

        val user = intent.getStringExtra("user")

        setContent {
            InterviewLoginTheme {
                Welcome(user)
            }
        }
    }
}

@Composable
fun Welcome(user: String?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome $user",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}