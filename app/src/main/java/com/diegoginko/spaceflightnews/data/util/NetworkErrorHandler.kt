package com.diegoginko.spaceflightnews.data.util

import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

object NetworkErrorHandler {

    /**
     * Detecta si un error es relacionado con conectividad de red
     */
    fun isNetworkError(exception: Throwable): Boolean {
        val isNetworkError = exception is IOException ||
                exception is UnknownHostException ||
                exception is SocketTimeoutException ||
                exception is SSLException ||
                (exception.message?.contains("Unable to resolve host", ignoreCase = true) == true) ||
                (exception.message?.contains("Network is unreachable", ignoreCase = true) == true) ||
                (exception.message?.contains("No address associated with hostname", ignoreCase = true) == true)
        
        if (isNetworkError) {
            Timber.d("Error de red detectado - tipo: ${exception.javaClass.simpleName}, mensaje: ${exception.message}")
        }
        
        return isNetworkError
    }

    /**
     * Obtiene un mensaje de error amigable para el usuario
     */
    fun getErrorMessage(exception: Throwable): String {
        return when {
            exception is UnknownHostException -> "No hay conexión a internet. Verifica tu conexión de red."
            exception is SocketTimeoutException -> "La conexión está tardando demasiado. Verifica tu conexión a internet."
            exception is IOException && exception.message?.contains("Unable to resolve host", ignoreCase = true) == true -> 
                "No se puede conectar al servidor. Verifica tu conexión a internet."
            isNetworkError(exception) -> "Error de conexión. Verifica que tengas internet disponible."
            else -> exception.message ?: "Ha ocurrido un error inesperado. Intenta nuevamente."
        }
    }
}
