package org.example.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class AlertMessage(
    val content: String,
    val detectedKeywords: List<String>,
    val severity: String,
    val timestamp: String = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
)