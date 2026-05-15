package org.example.alert

import java.text.Normalizer

// texto: minúsculas, sin acentos, sin caracteres especiales
fun String.normalize(): String {
    val normalized = Normalizer.normalize(this.lowercase(), Normalizer.Form.NFD)
    return normalized.replace(Regex("[^\\p{ASCII}]"), "")
}

fun String.extractKeywords(): List<String> {
    val normalized = this.normalize()
    return KeywordConfig.keywords.filter { keyword -> keyword.normalize() in normalized }
}

fun String.isCommand(): Boolean = this.startsWith("!")

fun String.toSafeLog(): String = this.replace("\n", " ").trim()