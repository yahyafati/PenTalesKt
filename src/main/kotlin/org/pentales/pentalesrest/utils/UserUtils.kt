package org.pentales.pentalesrest.utils

object UserUtils {

    private val backgroundForegroundCombos = listOf(
        "f44336" to "fff",
        "e91e63" to "fff",
        "2cb422" to "504848",
        "2196f3" to "000000",
        "ff9800" to "60a0a6",
        "9c27b0" to "fff",
        "3f51b5" to "fff",
        "009688" to "fff",
        "ff5722" to "fff",
        "607d8b" to "fff",
        "f44336" to "fff",
        "e91e63" to "fff",
    )

    fun getProfileAvatar(firstName: String, lastName: String): String {
        val (background, foreground) = backgroundForegroundCombos.random()
//        "https://ui-avatars.com/api/?name=${profile.firstName}+${profile.lastName}&background=$background&color=$foreground"
        return "https://ui-avatars.com/api/?name=${firstName}+${lastName}&background=$background&color=$foreground"
    }
}