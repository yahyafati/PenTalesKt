package org.pentales.pentalesrest.utils

import jakarta.servlet.http.*
import org.springframework.data.domain.*
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

        fun getPageRequest(
            page: Int?,
            size: Int?,
            sort: String?,
            direction: Sort.Direction?,
        ): PageRequest {
            val pageNumber = (page ?: 0).coerceAtLeast(0)
            val pageSize = (size ?: 10).coerceIn(1, MAX_PAGE_SIZE)
            val sortDirection = direction ?: Sort.Direction.ASC

            val pageRequest = if (sort.isNullOrEmpty()) {
                PageRequest.of(pageNumber, pageSize)
            } else {
                PageRequest.of(
                    pageNumber, pageSize, Sort.by(sortDirection, sort)
                )
            }
            return pageRequest
        }

        fun getPageRequest(
            pageParams: PageParams
        ): PageRequest {
            return getPageRequest(
                pageParams.page,
                pageParams.size,
                pageParams.sort,
                Sort.Direction.valueOf(pageParams.direction)
            )
        }

        private const val MAX_PAGE_SIZE = 50

        fun getPageRequest(): PageRequest {
            val pageParams = getPageParamsFromCurrentRequest()
            return getPageRequest(pageParams)
        }

    }
}