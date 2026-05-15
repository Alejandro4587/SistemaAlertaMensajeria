package org.example

import org.example.server.Server

fun main() {
    val server = Server(port = 9999)
    server.start()
}