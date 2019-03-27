package io.hauer

interface SwaggerBaseConfig {
    val base : Map<String,String>

    fun get(key: String) = base.getOrDefault(key, defaultValue)

    companion object {
        const val defaultValue = ""
        val defaultBase = hashMapOf("regex" to ".*", "ant" to "/**", "title" to "API Documentation")
    }
}