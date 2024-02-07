package org.pentales.pentalesrest.services

import jakarta.servlet.http.*

interface IProfilePageServices {

    fun getProfilePage(
        username: String,
        request: HttpServletRequest
    ): Map<String, Any>

}