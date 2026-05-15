package org.example.logging

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object SecurityLogger {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun log(level: String, message: String) {
        val timestamp = LocalDateTime.now().format(formatter)
        val logEntry = "[$timestamp] [$level] $message"
        println("LOG: $logEntry")

        try {
            val process = ProcessBuilder(
                "cmd", "/c", "echo $logEntry >> logs_seguridad.txt"
            )
                .redirectErrorStream(true)
                .start()
            process.waitFor()
        } catch (e: Exception) {
            println("Error escribiendo log: ${e.message}")
        }
    }
}