package com.diegoginko.spaceflightnews.presentation.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.diegoginko.spaceflightnews.R
import com.diegoginko.spaceflightnews.presentation.ui.theme.SpaceBlue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    // Animación de escala y opacidad
    val infiniteTransition = rememberInfiniteTransition(label = "splash_animation")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale_animation"
    )
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha_animation"
    )

    LaunchedEffect(Unit) {
        delay(SPLASH_DURATION)
        onNavigateToHome()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SpaceBlue),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.spaceflight_logo),
            contentDescription = "Space Flight News API Logo",
            modifier = Modifier
                .size(200.dp)
                .scale(scale)
                .alpha(alpha)
        )
    }
}

private const val SPLASH_DURATION = 1500L