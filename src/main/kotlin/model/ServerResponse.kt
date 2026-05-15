package org.example.model

sealed class ServerResponse {
    data class AlertResponse(
        val alert: AlertMessage,
        val message: String
    ) : ServerResponse()

    data class InfoResponse(
        val message: String
    ) : ServerResponse()

    data class StatsResponse(
        val totalMessages: Int,
        val totalAlerts: Int,
        val alertRate: String
    ) : ServerResponse()

    data class ErrorResponse(
        val reason: String
    ) : ServerResponse()
}