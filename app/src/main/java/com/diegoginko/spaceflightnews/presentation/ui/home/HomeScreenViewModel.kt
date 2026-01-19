package com.diegoginko.spaceflightnews.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegoginko.spaceflightnews.data.util.NetworkErrorHandler
import com.diegoginko.spaceflightnews.domain.model.Article
import com.diegoginko.spaceflightnews.domain.repository.SFNRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class HomeUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isNetworkError: Boolean = false,
    val searchQuery: String = ""
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: SFNRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        Timber.d("HomeScreenViewModel inicializado")
        loadArticles()
    }

    fun loadArticles(searchQuery: String = "") {
        viewModelScope.launch {
            Timber.d("Cargando artículos - búsqueda: '$searchQuery'")
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                searchQuery = searchQuery
            )

            repository.getArticles(
                limit = 50,
                search = searchQuery.takeIf { it.isNotBlank() }
            ).fold(
                onSuccess = { paginatedResult ->
                    Timber.d("Artículos cargados exitosamente - cantidad: ${paginatedResult.results.size}")
                    _uiState.value = _uiState.value.copy(
                        articles = paginatedResult.results,
                        isLoading = false,
                        error = null,
                        isNetworkError = false
                    )
                },
                onFailure = { exception ->
                    val isNetworkError = NetworkErrorHandler.isNetworkError(exception)
                    val errorMessage = NetworkErrorHandler.getErrorMessage(exception)
                    Timber.w(exception, "Error al cargar artículos - esErrorRed: $isNetworkError, mensaje: $errorMessage")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = errorMessage,
                        isNetworkError = isNetworkError
                    )
                }
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        Timber.d("Búsqueda cambiada: '$query'")
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun performSearch() {
        Timber.d("Ejecutando búsqueda con query: '${_uiState.value.searchQuery}'")
        loadArticles(_uiState.value.searchQuery)
    }
}