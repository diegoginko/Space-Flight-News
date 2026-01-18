package com.diegoginko.spaceflightnews.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.diegoginko.spaceflightnews.presentation.navigation.NavigationWrapper
import com.diegoginko.spaceflightnews.presentation.ui.theme.SpaceFlightNewsTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun Main(){
    val modifier = Modifier.fillMaxSize()
    SpaceFlightNewsTheme{
        NavigationWrapper()
    }
}
