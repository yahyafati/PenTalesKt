package org.pentales.pentalesrest.utils

import jakarta.servlet.http.*
import org.springframework.data.domain.*
import org.springframework.web.context.request.*

class ServletUtil {

    data class PageParams(
        var page: Int,
        var size: Int,
        var sort: Sort,
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

        private fun getPageParams(request: HttpServletRequest): PageParams {
            val page = request.getParameter("page")?.toIntOrNull() ?: 0
            val size = request.getParameter("size")?.toIntOrNull() ?: 10
            val sort = if (request.getParameter("sort").isNullOrBlank()) "id" else request.getParameter("sort")
            val direction = request.getParameter("direction") ?: "ASC"

            return PageParams(
                page, size,
                Sort.by(Sort.Direction.valueOf(direction), sort),
            )
        }

        fun getPageParamsFromCurrentRequest(): PageParams {
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)?.request
                ?: throw IllegalStateException("No request found")
            return getPageParams(request)
        }

        private fun getPageRequest(
            page: Int?,
            size: Int?,
            sort: Sort?,
        ): PageRequest {
            val pageNumber = (page ?: 0).coerceAtLeast(0)
            val pageSize = (size ?: 10).coerceIn(1, MAX_PAGE_SIZE)

            val pageRequest = if (sort == null) {
                PageRequest.of(pageNumber, pageSize)
            } else {
                PageRequest.of(
                    pageNumber, pageSize, sort
                )
            }
            return pageRequest
        }

        fun getPageRequest(
            page: Int?,
            size: Int?,
            sort: String?,
            direction: Sort.Direction?,
        ): PageRequest {
            val sortObj = if (sort != null && direction != null) {
                Sort.by(direction, sort)
            } else {
                null
            }
            return getPageRequest(page, size, sortObj)
        }

        fun getPageRequest(
            pageParams: PageParams
        ): PageRequest {
            return getPageRequest(
                pageParams.page,
                pageParams.size,
                pageParams.sort
            )
        }

        private const val MAX_PAGE_SIZE = 50

        fun getPageRequest(): PageRequest {
            val pageParams = getPageParamsFromCurrentRequest()
            return getPageRequest(pageParams)
        }

    }
}