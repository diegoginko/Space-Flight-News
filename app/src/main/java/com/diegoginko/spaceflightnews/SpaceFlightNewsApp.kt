package com.diegoginko.spaceflightnews

import android.app.Application
import android.content.pm.ApplicationInfo
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SpaceFlightNewsApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Inicializar Timber
        val isDebug = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        if (isDebug) {
            Timber.plant(Timber.DebugTree())
        } else {
            // En producción, puedes usar un Tree personalizado que envíe logs a un servicio
            Timber.plant(Timber.DebugTree()) // Por ahora usamos DebugTree también en release
        }
        
        Timber.d("SpaceFlightNewsApp inicializado")
    }
}