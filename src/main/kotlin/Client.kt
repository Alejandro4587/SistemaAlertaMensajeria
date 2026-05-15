    package org.example

    import java.io.BufferedReader
    import java.io.InputStreamReader
    import java.io.PrintWriter
    import java.net.Socket

    fun main() {
        val socket = Socket("localhost", 9999)
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        val output = PrintWriter(socket.getOutputStream(), true)
        val keyboard = BufferedReader(InputStreamReader(System.`in`))

        // Hilo para recibir mensajes del servidor
        Thread {
            var line: String?
            while (input.readLine().also { line = it } != null) {
                println(line)
            }
        }.start()

        // Enviar mensajes desde teclado
        var userInput: String?
        while (keyboard.readLine().also { userInput = it } != null) {
            output.println(userInput)
        }

        socket.close()
    }