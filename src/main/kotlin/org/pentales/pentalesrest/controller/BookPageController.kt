package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.book.*
import org.pentales.pentalesrest.services.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book")
class BookPageController(private val bookPageServices: IBookPageServices) {

    @PostMapping
    fun getBookPage(
        @RequestParam
        page: Int?,
        @RequestParam
        size: Int?,
        @RequestBody
        filters: List<FilterDto> = listOf()
    ): ResponseEntity<Page<BookDTO>> {
        val pageNumber = page ?: 0
        val pageSize = size ?: 10

        val books = bookPageServices.getBooks(
            pageNumber, pageSize, filters
        )

        return ResponseEntity.ok(books)
    }

    @GetMapping("/{id}")
    fun getBookById(
        @PathVariable
        id: Long
    ): ResponseEntity<Map<String, Any>> {
        val bookData = bookPageServices.getBookPageData(id)

        return ResponseEntity.ok(bookData)
    }

}