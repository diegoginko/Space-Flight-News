package com.diegoginko.spaceflightnews.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegoginko.spaceflightnews.data.util.NetworkErrorHandler
import com.diegoginko.spaceflightnews.domain.model.Article
import com.diegoginko.spaceflightnews.domain.model.EventDetail
import com.diegoginko.spaceflightnews.domain.model.LaunchDetail
import com.diegoginko.spaceflightnews.domain.repository.SFNRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ArticleDetailUiState(
    val article: Article? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isNetworkError: Boolean = false,
    val launchDetails: Map<String, LaunchDetail> = emptyMap(),
    val eventDetails: Map<Int, EventDetail> = emptyMap(),
    val loadingLaunches: Set<String> = emptySet(),
    val loadingEvents: Set<Int> = emptySet()
)

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val repository: SFNRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleDetailUiState())
    val uiState: StateFlow<ArticleDetailUiState> = _uiState.asStateFlow()

    fun loadArticle(articleId: Int) {
        viewModelScope.launch {
            Timber.d("Cargando detalle del artículo - id: $articleId")
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            repository.getArticleById(articleId).fold(
                onSuccess = { article ->
                    Timber.d("Artículo cargado exitosamente - título: ${article.title}")
                    _uiState.value = _uiState.value.copy(
                        article = article,
                        isLoading = false,
                        error = null,
                        isNetworkError = false
                    )
                },
                onFailure = { exception ->
                    val isNetworkError = NetworkErrorHandler.isNetworkError(exception)
                    val errorMessage = NetworkErrorHandler.getErrorMessage(exception)
                    Timber.w(exception, "Error al cargar artículo - id: $articleId, esErrorRed: $isNetworkError, mensaje: $errorMessage")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = errorMessage,
                        isNetworkError = isNetworkError
                    )
                }
            )
        }
    }

    fun loadLaunchDetail(launchId: String) {
        // Verificar si ya está cargado o cargando
        if (_uiState.value.launchDetails.containsKey(launchId) || 
            _uiState.value.loadingLaunches.contains(launchId)) {
            return
        }

        viewModelScope.launch {
            Timber.d("Cargando detalles del lanzamiento - id: $launchId")
            _uiState.value = _uiState.value.copy(
                loadingLaunches = _uiState.value.loadingLaunches + launchId
            )

            repository.getLaunchById(launchId).fold(
                onSuccess = { launchDetail ->
                    Timber.d("Detalles del lanzamiento cargados exitosamente - nombre: ${launchDetail.name}")
                    _uiState.value = _uiState.value.copy(
                        launchDetails = _uiState.value.launchDetails + (launchId to launchDetail),
                        loadingLaunches = _uiState.value.loadingLaunches - launchId
                    )
                },
                onFailure = { exception ->
                    Timber.w(exception, "Error al cargar detalles del lanzamiento - id: $launchId")
                    _uiState.value = _uiState.value.copy(
                        loadingLaunches = _uiState.value.loadingLaunches - launchId
                    )
                }
            )
        }
    }

    fun loadEventDetail(eventId: Int) {
        // Verificar si ya está cargado o cargando
        if (_uiState.value.eventDetails.containsKey(eventId) || 
            _uiState.value.loadingEvents.contains(eventId)) {
            return
        }

        viewModelScope.launch {
            Timber.d("Cargando detalles del evento - id: $eventId")
            _uiState.value = _uiState.value.copy(
                loadingEvents = _uiState.value.loadingEvents + eventId
            )

            repository.getEventById(eventId).fold(
                onSuccess = { eventDetail ->
                    Timber.d("Detalles del evento cargados exitosamente - nombre: ${eventDetail.name}")
                    _uiState.value = _uiState.value.copy(
                        eventDetails = _uiState.value.eventDetails + (eventId to eventDetail),
                        loadingEvents = _uiState.value.loadingEvents - eventId
                    )
                },
                onFailure = { exception ->
                    Timber.w(exception, "Error al cargar detalles del evento - id: $eventId")
                    _uiState.value = _uiState.value.copy(
                        loadingEvents = _uiState.value.loadingEvents - eventId
                    )
                }
            )
        }
    }

    fun loadAllLaunchAndEventDetails() {
        val article = _uiState.value.article ?: return
        
        // Cargar todos los detalles de lanzamientos
        article.launches.forEach { launch ->
            loadLaunchDetail(launch.id)
        }
        
        // Cargar todos los detalles de eventos
        article.events.forEach { event ->
            loadEventDetail(event.id)
        }
    }
}
