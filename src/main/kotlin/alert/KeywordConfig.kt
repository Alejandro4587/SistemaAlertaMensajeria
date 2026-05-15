package org.example.alert

object KeywordConfig {
    val keywords = listOf(
        // Fuego
        "fuego", "incendi", "llama", "quemand", "humo",
        // Médico
        "emergencia", "ayuda", "herid", "muriend", "muert",
        "sangr", "desmay", "inconscient", "infart", "accident",
        // Seguridad
        "peligr", "urgent", "sos", "alarm", "rob", "ataqu",
        "amenaz", "arma", "dispar", "explosion",
        // Desastres
        "inundacion", "terremot", "derrumb", "escap", "gas",
        // Peticiones de auxilio
        "socorro", "auxili", "llamad", "ambulanci", "polici"
    )

    fun getSeverity(detected: List<String>): String {
        return when {
            detected.size >= 3 -> "ALTA"
            detected.size == 2 -> "MEDIA"
            else -> "BAJA"
        }
    }
}