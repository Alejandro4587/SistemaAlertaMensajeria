package org.example.server

import org.example.alert.AlertDetector
import org.example.alert.isCommand
import org.example.alert.toSafeLog
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ClientHandler(private val socket: Socket) : Runnable {

    override fun run() {
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        val output = PrintWriter(socket.getOutputStream(), true)

        output.println("Conectado al Sistema de Alerta.")
        output.println("Escribe tu mensaje o '!stats' para ver estadísticas:")

        try {
            var message: String?
            while (input.readLine().also { message = it } != null) {
                val msg = message!!.trim()
                println("[${socket.inetAddress.hostAddress}]: ${msg.toSafeLog()}")

                if (msg.isCommand()) {
                    when (msg) {
                        "!stats" -> {
                            val stats = AlertDetector.getStats()
                            output.println("ESTADÍSTICAS:")
                            output.println("Total mensajes: ${stats.totalMessages}")
                            output.println("Total alertas:  ${stats.totalAlerts}")
                            output.println("Tasa de alertas: ${stats.alertRate}")
                        }
                        else -> output.println("Comando no reconocido: $msg")
                    }
                } else {
                    AlertDetector.analyze(msg, output)
                }
            }
        } catch (e: Exception) {
            println("Cliente desconectado: ${e.message}")
        } finally {
            socket.close()
        }
    }
}