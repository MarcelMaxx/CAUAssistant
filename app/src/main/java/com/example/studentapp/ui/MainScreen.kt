package com.example.studentapp.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Scaffold

@Composable
    fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val items = listOf("Home" to Icons.Default.Home, "Notifications" to Icons.Default.Notifications)
                items.forEach { (label, icon) ->
                    BottomNavigationItem(
                        selected = false,
                        onClick = {
                            navController.navigate(label)
                        },
                        icon = { Icon(imageVector = icon, contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "Home"
        ) {
            composable("Home") { HomePage() }
            composable("Notifications") { NotificationsPage() }
        }
    }
}
