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
import com.example.studentapp.ui.theme.AppColors

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = AppColors.Primary
            ) {
                val items = listOf("캡퍼스" to Icons.Default.Home, "개인" to Icons.Default.Notifications)
                items.forEach { (label, icon) ->
                    BottomNavigationItem(
                        selected = false,
                        onClick = {
                            navController.navigate(label)
                        },
                        icon = { Icon(
                            imageVector = icon, 
                            contentDescription = label,
                            tint = AppColors.OnPrimary
                        ) },
                        label = { Text(
                            text = label,
                            color = AppColors.OnPrimary
                        ) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "캡퍼스"
        ) {
            composable("캡퍼스") { CampusPage() }
            composable("개인") { PersonalPage() }
        }
    }
}
