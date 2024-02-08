package org.pentales.pentalesrest.utils

import jakarta.servlet.http.*
import org.springframework.web.context.request.*

class ServletUtil {

    companion object {

        private fun getBaseURL(request: HttpServletRequest): String {
            return request.requestURL.toString().substringBefore(request.requestURI)
        }

        fun getBaseURLFromCurrentRequest(): String {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)?.request
                ?: throw IllegalStateException("No request found")
            return getBaseURL(request)
        }

    }
}