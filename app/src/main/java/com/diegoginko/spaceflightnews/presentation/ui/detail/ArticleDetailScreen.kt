package com.diegoginko.spaceflightnews.presentation.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.diegoginko.spaceflightnews.domain.model.LaunchDetail
import com.diegoginko.spaceflightnews.presentation.util.DateFormatter
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    articleId: Int,
    onNavigateBack: () -> Unit,
    viewModel: ArticleDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(articleId) {
        viewModel.loadArticle(articleId)
    }

    // Cargar detalles de lanzamientos y eventos cuando el artículo esté disponible
    LaunchedEffect(uiState.article) {
        uiState.article?.let {
            viewModel.loadAllLaunchAndEventDetails()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Artículo") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            if (uiState.isNetworkError) {
                                Text(
                                    text = "📡",
                                    style = MaterialTheme.typography.displayLarge
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            Text(
                                text = uiState.error ?: "Error desconocido",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (uiState.isNetworkError) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.error
                                },
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.loadArticle(articleId) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Reintentar")
                            }
                        }
                    }
                }
                uiState.article != null -> {
                    ArticleDetailContent(
                        article = uiState.article!!,
                        launchDetails = uiState.launchDetails,
                        loadingLaunches = uiState.loadingLaunches,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleDetailContent(
    article: com.diegoginko.spaceflightnews.domain.model.Article,
    launchDetails: Map<String, com.diegoginko.spaceflightnews.domain.model.LaunchDetail> = emptyMap(),
    loadingLaunches: Set<String> = emptySet(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        // Imagen del artículo
        AsyncImage(
            model = article.imageUrl ?: "",
            contentDescription = article.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Sitio de noticias
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    text = article.newsSite,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontWeight = FontWeight.Medium
                )
            }

            // Título
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Fecha
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Publicado: ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = DateFormatter.formatToDDMMYYYY(article.publishedAt),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }

            // Autores
            if (article.authors.isNotEmpty()) {
                Text(
                    text = "Autor${if (article.authors.size > 1) "es" else ""}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                article.authors.forEach { author ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "✍️",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                Text(
                                    text = author.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            // Redes sociales del autor
                            author.socials?.let { socials ->
                                Row(
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    socials.x?.let { xUrl ->
                                        Text(
                                            text = "𝕏",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.clickable {
                                                try {
                                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(xUrl))
                                                    context.startActivity(intent)
                                                } catch (e: Exception) {
                                                    Timber.e(e, "Error al abrir URL de X: $xUrl")
                                                }
                                            }
                                        )
                                    }
                                    socials.youtube?.let { youtubeUrl ->
                                        Text(
                                            text = "▶️",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.clickable {
                                                try {
                                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                                                    context.startActivity(intent)
                                                } catch (e: Exception) {
                                                    Timber.e(e, "Error al abrir URL de YouTube: $youtubeUrl")
                                                }
                                            }
                                        )
                                    }
                                    socials.instagram?.let { instagramUrl ->
                                        Text(
                                            text = "📷",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.clickable {
                                                try {
                                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
                                                    context.startActivity(intent)
                                                } catch (e: Exception) {
                                                    Timber.e(e, "Error al abrir URL de Instagram: $instagramUrl")
                                                }
                                            }
                                        )
                                    }
                                    socials.linkedin?.let { linkedinUrl ->
                                        Text(
                                            text = "💼",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.clickable {
                                                try {
                                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl))
                                                    context.startActivity(intent)
                                                } catch (e: Exception) {
                                                    Timber.e(e, "Error al abrir URL de LinkedIn: $linkedinUrl")
                                                }
                                            }
                                        )
                                    }
                                    socials.mastodon?.let { mastodonUrl ->
                                        Text(
                                            text = "🐘",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.clickable {
                                                try {
                                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mastodonUrl))
                                                    context.startActivity(intent)
                                                } catch (e: Exception) {
                                                    Timber.e(e, "Error al abrir URL de Mastodon: $mastodonUrl")
                                                }
                                            }
                                        )
                                    }
                                    socials.bluesky?.let { blueskyUrl ->
                                        Text(
                                            text = "☁️",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.clickable {
                                                try {
                                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(blueskyUrl))
                                                    context.startActivity(intent)
                                                } catch (e: Exception) {
                                                    Timber.e(e, "Error al abrir URL de Bluesky: $blueskyUrl")
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Resumen
            article.summary?.let { summary ->
                Text(
                    text = "Resumen",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = summary,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Lanzamientos
            if (article.launches.isNotEmpty()) {
                Text(
                    text = "Lanzamientos",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                article.launches.forEach { launch ->
                    val launchDetail = launchDetails[launch.id]
                    val isLoadingLaunch = loadingLaunches.contains(launch.id)
                    val hasFailed = !isLoadingLaunch && launchDetail == null
                    
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            // Si está cargando, solo mostrar el indicador
                            if (isLoadingLaunch) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "🚀",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(end = 12.dp)
                                    )
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = MaterialTheme.colorScheme.primary,
                                        strokeWidth = 2.dp
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Cargando detalles del lanzamiento...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            } else {
                                // Si no está cargando, mostrar información (detalle o básica si falló)
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "🚀",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(end = 12.dp)
                                    )
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = launchDetail?.name ?: launch.id,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = launchDetail?.launchServiceProvider?.name ?: launch.provider,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                        // Mostrar mensaje si falló la carga
                                        if (hasFailed) {
                                            Text(
                                                text = "No se pudieron cargar los detalles",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.error,
                                                modifier = Modifier.padding(top = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                            
                            // Información detallada si está disponible (solo si no está cargando)
                            if (!isLoadingLaunch) {
                                launchDetail?.let { detail ->
                                Column(
                                    modifier = Modifier.padding(top = 12.dp)
                                ) {
                                    // Estado del lanzamiento
                                    detail.status?.let { status ->
                                        Surface(
                                            color = when (status.abbrev) {
                                                "Go" -> MaterialTheme.colorScheme.tertiaryContainer
                                                "TBC" -> MaterialTheme.colorScheme.surfaceVariant
                                                else -> MaterialTheme.colorScheme.errorContainer
                                            },
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.padding(bottom = 8.dp)
                                        ) {
                                            Text(
                                                text = status.name ?: status.abbrev ?: "Desconocido",
                                                style = MaterialTheme.typography.labelSmall,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                color = when (status.abbrev) {
                                                    "Go" -> MaterialTheme.colorScheme.onTertiaryContainer
                                                    "TBC" -> MaterialTheme.colorScheme.onSurfaceVariant
                                                    else -> MaterialTheme.colorScheme.onErrorContainer
                                                }
                                            )
                                        }
                                    }
                                    
                                    // Fecha del lanzamiento
                                    detail.netDate?.let { date ->
                                        Row(
                                            modifier = Modifier.padding(bottom = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "📅 ",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                            Text(
                                                text = "Fecha: ${DateFormatter.formatToDDMMYYYY(date)}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                    
                                    // Información del cohete
                                    detail.rocket?.configuration?.let { rocketConfig ->
                                        Row(
                                            modifier = Modifier.padding(bottom = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "🚀 ",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                            Text(
                                                text = "Cohete: ${rocketConfig.name ?: rocketConfig.fullName ?: "Desconocido"}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                    
                                    // Información de la misión
                                    detail.mission?.let { mission ->
                                        mission.name?.let { missionName ->
                                            Row(
                                                modifier = Modifier.padding(bottom = 4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "🎯 ",
                                                    style = MaterialTheme.typography.bodySmall
                                                )
                                                Text(
                                                    text = "Misión: $missionName",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }
                                        mission.orbit?.let { orbit ->
                                            Text(
                                                text = "   Órbita: ${orbit.name ?: orbit.abbrev ?: "Desconocida"}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
                                            )
                                        }
                                    }
                                    
                                    // Ubicación del lanzamiento
                                    detail.pad?.let { pad ->
                                        Row(
                                            modifier = Modifier.padding(bottom = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "📍 ",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                            Column {
                                                pad.name?.let { name ->
                                                    Text(
                                                        text = "Plataforma: $name",
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                                pad.location?.name?.let { locationName ->
                                                    Text(
                                                        text = "Ubicación: $locationName",
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    
                                    // Probabilidad
                                    detail.probability?.let { probability ->
                                        Row(
                                            modifier = Modifier.padding(bottom = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "📊 ",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                            Text(
                                                text = "Probabilidad: $probability%",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para abrir en navegador
            Button(
                onClick = {
                    try {
                        Timber.d("Abriendo URL del artículo: ${article.url}")
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Timber.e(e, "Error al abrir la URL: ${article.url}")
                        // Si no hay navegador disponible o hay un error, el log queda registrado
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Text(
                    text = "Ver artículo completo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
}
