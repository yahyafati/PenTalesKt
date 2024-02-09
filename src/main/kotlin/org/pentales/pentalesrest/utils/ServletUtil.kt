package org.pentales.pentalesrest.utils

import jakarta.servlet.http.*
import org.springframework.web.context.request.*

class ServletUtil {

    data class PageParams(
        val page: Int,
        val size: Int,
        val sort: String,
        val direction: String,
    )

    companion object {

        private fun getBaseURL(request: HttpServletRequest): String {
            return request.requestURL.toString().substringBefore(request.requestURI)
        }

        fun getBaseURLFromCurrentRequest(): String {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)?.request
                ?: throw IllegalStateException("No request found")
            return getBaseURL(request)
        }

        fun getPageParams(request: HttpServletRequest): PageParams {
            val page = request.getParameter("page")?.toIntOrNull() ?: 0
            val size = request.getParameter("size")?.toIntOrNull() ?: 10
            val sort = request.getParameter("sort") ?: ""
            val direction = request.getParameter("direction") ?: "ASC"
            return PageParams(page, size, sort, direction)
        }

        fun getPageParamsFromCurrentRequest(): PageParams {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)?.request
                ?: throw IllegalStateException("No request found")
            return getPageParams(request)
        }

    }
}