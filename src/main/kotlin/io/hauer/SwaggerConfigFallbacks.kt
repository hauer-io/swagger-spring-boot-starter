package io.hauer

class SwaggerConfigFallbacks {
    private val fallbacks = HashMap<String,String>()

    fun getFallback(key:String) = fallbacks.getOrDefault(key,"")

    fun setFallback(key: String, value : String) = fallbacks.set(key,value)
}