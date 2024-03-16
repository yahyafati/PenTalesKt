package org.pentales.pentalesrest.utils

object ObjectUtils {

    fun toJson(obj: Any): String {
        return com.google.gson.Gson().toJson(obj)
    }
    
}