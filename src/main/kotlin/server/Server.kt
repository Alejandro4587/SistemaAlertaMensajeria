package org.example.server

import java.net.ServerSocket

class Server(private val port: Int) {

    fun start() {
        val serverSocket = ServerSocket(port)
        println("Servidor iniciado en el puerto $port")
        println("Esperando conexiones...")

        while (true) {
            val clientSocket = serverSocket.accept()
            println("Cliente conectado: ${clientSocket.inetAddress.hostAddress}")
            Thread(ClientHandler(clientSocket)).start()
        }
    }
}