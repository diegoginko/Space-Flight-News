package com.diegoginko.spaceflightnews.presentation.navigation

sealed class Routes(val route: String) {
    data object Splash : Routes("splash")
    data object Home : Routes("home")
    data object ArticleDetail : Routes("article_detail") {
        fun createRoute(articleId: Int) = "article_detail/$articleId"
    }

    companion object {
        const val ARTICLE_ID_ARG = "articleId"
    }
}