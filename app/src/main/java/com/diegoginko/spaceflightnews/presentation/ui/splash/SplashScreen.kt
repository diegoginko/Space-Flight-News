package com.diegoginko.spaceflightnews.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.diegoginko.spaceflightnews.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {


    LaunchedEffect(Unit) {
        delay(SPLASH_DURATION)
        onNavigateToHome()
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.spaceflight_logo),
            contentDescription = "Space Flight API Logo",
            modifier = Modifier
                .wrapContentSize()
        )
    }
}

private const val SPLASH_DURATION = 800L