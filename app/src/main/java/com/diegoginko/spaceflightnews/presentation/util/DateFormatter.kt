package com.diegoginko.spaceflightnews.presentation.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateFormatter {
    
    /**
     * Formatea una fecha ISO 8601 a formato dd/mm/aaaa
     * Ejemplo: "2026-01-18T16:38:11.933Z" -> "18/01/2026"
     */
    fun formatToDDMMYYYY(dateString: String?): String {
        if (dateString.isNullOrBlank()) return ""
        
        return try {
            // Parsear la fecha ISO 8601
            // La fecha viene como "2026-01-18T16:38:11.933Z" o similar
            val dateTimeString = dateString.replace("Z", "")
            val parts = dateTimeString.split("T")
            
            if (parts.isNotEmpty()) {
                val datePart = parts[0] // "2026-01-18"
                val dateParts = datePart.split("-")
                
                if (dateParts.size == 3) {
                    val year = dateParts[0]
                    val month = dateParts[1]
                    val day = dateParts[2]
                    "$day/$month/$year"
                } else {
                    dateString // Si no se puede parsear, devolver el original
                }
            } else {
                dateString
            }
        } catch (e: Exception) {
            // Si hay algún error al parsear, devolver el string original
            dateString
        }
    }
    
    /**
     * Formatea una fecha ISO 8601 a formato dd/mm/aaaa HH:mm
     * Ejemplo: "2026-01-18T16:38:11.933Z" -> "18/01/2026 16:38"
     */
    fun formatToDDMMYYYYHHMM(dateString: String?): String {
        if (dateString.isNullOrBlank()) return ""
        
        return try {
            val dateTimeString = dateString.replace("Z", "")
            val parts = dateTimeString.split("T")
            
            if (parts.size == 2) {
                val datePart = parts[0] // "2026-01-18"
                val timePart = parts[1] // "16:38:11.933"
                val timeParts = timePart.split(":")
                
                val dateParts = datePart.split("-")
                
                if (dateParts.size == 3 && timeParts.size >= 2) {
                    val year = dateParts[0]
                    val month = dateParts[1]
                    val day = dateParts[2]
                    val hour = timeParts[0]
                    val minute = timeParts[1]
                    "$day/$month/$year $hour:$minute"
                } else {
                    formatToDDMMYYYY(dateString) // Fallback a solo fecha
                }
            } else {
                formatToDDMMYYYY(dateString) // Fallback a solo fecha
            }
        } catch (e: Exception) {
            formatToDDMMYYYY(dateString) // Fallback a solo fecha
        }
    }
}
