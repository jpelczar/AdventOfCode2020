package io.jpelczar.utils

class Reader(private val path: String) {
    fun <T> asResource(work: (String) -> T): T {
        val content = this::class.java.classLoader.getResource(path)?.readText()
        return work(content ?: "")
    }
}


