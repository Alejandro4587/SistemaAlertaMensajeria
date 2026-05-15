package org.example.alert

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.example.logging.SecurityLogger
import org.example.model.AlertMessage
import org.example.model.ServerResponse
import org.example.notification.TelegramNotifier
import java.io.PrintWriter
import java.util.concurrent.atomic.AtomicInteger

object AlertDetector {

    val totalAlerts = AtomicInteger(0)
    val totalMessages = AtomicInteger(0)

    fun analyze(message: String, output: PrintWriter) {
        totalMessages.incrementAndGet()

        // Extension function para detectar keywords
        val detected = message.extractKeywords()
        val response = buildResponse(message, detected)

        handleResponse(response, output)
    }

    private fun buildResponse(message: String, detected: List<String>): ServerResponse {
        return if (detected.isNotEmpty()) {
            val severity = KeywordConfig.getSeverity(detected)
            val alert = AlertMessage(message, detected, severity)
            totalAlerts.incrementAndGet()
            ServerResponse.AlertResponse(alert, "ALERTA $severity detectada. Notificando...")
        } else {
            ServerResponse.InfoResponse("Mensaje recibido: $message")
        }
    }

    private fun handleResponse(response: ServerResponse, output: PrintWriter) {
        when (response) {
            is ServerResponse.AlertResponse -> {
                println("ALERTA: ${response.alert.severity} | Keywords: ${response.alert.detectedKeywords}")
                output.println(response.message)

                // async/await para no bloquear el hilo principal
                CoroutineScope(Dispatchers.IO).async {
                    TelegramNotifier.sendAlert(response.alert)
                }

                SecurityLogger.log("ALERTA", "${response.alert.severity} | ${response.alert.content}")
            }
            is ServerResponse.InfoResponse -> {
                output.println(response.message)
                SecurityLogger.log("INFO", response.message)
            }
            is ServerResponse.StatsResponse -> {
                output.println("Mensajes: ${response.totalMessages} | Alertas: ${response.totalAlerts} | Tasa: ${response.alertRate}")
            }
            is ServerResponse.ErrorResponse -> {
                output.println("Error: ${response.reason}")
                SecurityLogger.log("ERROR", response.reason)
            }
        }
    }

    fun getStats(): ServerResponse.StatsResponse {
        val total = totalMessages.get()
        val alerts = totalAlerts.get()
        val rate = if (total > 0) "${"%.1f".format(alerts * 100.0 / total)}%" else "0%"
        return ServerResponse.StatsResponse(total, alerts, rate)
    }
}