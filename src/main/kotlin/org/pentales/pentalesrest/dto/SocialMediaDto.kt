package org.pentales.pentalesrest.dto

class SocialMediaDto(
    private val name: String = "",
    private val url: String = "",
) {

    override fun toString(): String {
        return "SocialMediaDto(name='$name', url='$url')"
    }
}