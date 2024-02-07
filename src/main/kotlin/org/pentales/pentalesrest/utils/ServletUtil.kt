package org.pentales.pentalesrest.utils

import jakarta.servlet.http.*

class ServletUtil {

    companion object {

        fun getBaseURL(request: HttpServletRequest): String {
            return request.requestURL.toString().substringBefore(request.requestURI)
        }
    }
}