package org.pentales.pentalesrest.services

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.book.*
import org.springframework.data.domain.*

interface IBookPageServices {

    fun getBooks(page: Int, size: Int, filters: List<FilterDto>): Page<BookDTO>
    fun getBookPageData(bookId: Long): Map<String, Any>
}