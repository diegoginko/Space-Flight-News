package com.diegoginko.spaceflightnews.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.diegoginko.spaceflightnews.presentation.ui.detail.ArticleDetailScreen
import com.diegoginko.spaceflightnews.presentation.ui.home.HomeScreen
import com.diegoginko.spaceflightnews.presentation.ui.splash.SplashScreen
import timber.log.Timber

@Composable
fun NavigationWrapper() {
    val mainNavController = rememberNavController()
    Timber.d("NavigationWrapper inicializado")
    
    NavHost(
        navController = mainNavController,
        startDestination = Routes.Splash.route
    ) {
        composable(route = Routes.Splash.route) {
            Timber.d("Navegando a pantalla Splash")
            SplashScreen(
                onNavigateToHome = {
                    Timber.d("Navegando de Splash a Home")
                    mainNavController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Routes.Home.route) {
            Timber.d("Navegando a pantalla Home")
            HomeScreen(
                onArticleClick = { articleId ->
                    Timber.d("Navegando a detalle de artículo - id: $articleId")
                    mainNavController.navigate(Routes.ArticleDetail.createRoute(articleId))
                }
            )
        }
        composable(
            route = "${Routes.ArticleDetail.route}/{${Routes.ARTICLE_ID_ARG}}",
            arguments = listOf(
                navArgument(Routes.ARTICLE_ID_ARG) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getInt(Routes.ARTICLE_ID_ARG) ?: 0
            Timber.d("Navegando a pantalla de detalle de artículo - id: $articleId")
            ArticleDetailScreen(
                articleId = articleId,
                onNavigateBack = {
                    Timber.d("Regresando desde detalle de artículo")
                    mainNavController.popBackStack()
                }
            )
        }
    }
}