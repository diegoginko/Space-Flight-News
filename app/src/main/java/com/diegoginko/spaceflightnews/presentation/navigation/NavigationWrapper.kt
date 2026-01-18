package com.diegoginko.spaceflightnews.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.diegoginko.spaceflightnews.presentation.ui.home.HomeScreen
import com.diegoginko.spaceflightnews.presentation.ui.splash.SplashScreen

@Composable
fun NavigationWrapper() {
    val mainNavController = rememberNavController()
    NavHost(navController = mainNavController, startDestination = Routes.Splash.route) {
        composable(route = Routes.Splash.route) {
            SplashScreen(
                onNavigateToHome = { mainNavController.navigate(Routes.Home.route) }
            )
        }
        composable(route = Routes.Home.route) {
            HomeScreen(

            )
        }
    }
}