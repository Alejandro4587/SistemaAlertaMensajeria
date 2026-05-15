package org.example.notification

import org.example.model.AlertMessage
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object TelegramNotifier {

    private const val BOT_TOKEN = "8716618163:AAGIToh4M0el-OB8Bgc0IEibtlbKMAOjdrU"
    private const val CHAT_ID = "6190701542"

    suspend fun sendAlert(alert: AlertMessage) {
        try {
            val text = URLEncoder.encode("""
                ALERTA DEL SISTEMA
                ─────────────────────
                Hora: ${alert.timestamp}
                Severidad: ${alert.severity}
                Keywords: ${alert.detectedKeywords.joinToString(", ")}
                Mensaje: ${alert.content}
            """.trimIndent(), "UTF-8")

            val url = URL("https://api.telegram.org/bot$BOT_TOKEN/sendMessage?chat_id=$CHAT_ID&text=$text")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val responseCode = connection.responseCode
            if (responseCode == 200) {
                println("Notificación Telegram enviada correctamente")
            } else {
                println("Error Telegram: código $responseCode")
            }
            connection.disconnect()
        } catch (e: Exception) {
            println("Excepción Telegram: ${e.message}")
        }
    }
}