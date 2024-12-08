package com.example.studentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.studentapp.ui.HomePage
import com.example.studentapp.ui.MainScreen
import com.example.studentapp.ui.theme.StudentAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentAppTheme {
                MainScreen()
            }
        }
    }
}