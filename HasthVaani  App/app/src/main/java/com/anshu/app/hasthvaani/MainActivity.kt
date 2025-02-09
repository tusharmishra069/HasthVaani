package com.anshu.app.hasthvaani

import DashboardScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anshu.app.hasthvaani.ui.theme.HasthVaaniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false) // Edge-to-edge content
        setContent {
            HasthVaaniTheme {
                val navController = rememberNavController()

                // Navigation Setup
                Surface {
                    NavHost(navController = navController, startDestination = "splash_screen") {
                        composable("splash_screen") { SplashScreen(navController) }
                        composable("dashboard_screen") { DashboardScreen(navController) }
                    }
                }
            }
        }
    }
}
