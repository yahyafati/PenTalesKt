package org.pentales.pentalesrest.services

interface IProfilePageServices {

    fun getProfilePage(
        username: String,
    ): Map<String, Any>

}