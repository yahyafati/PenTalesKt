package org.pentales.pentalesrest.controller.book

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book/{bookId}/publishers")
class BookPublisherController(private val bookIntermediatesServices: IBookIntermediatesServices) {

    val service: IBookIntermediatesServices
        get() = bookIntermediatesServices

    @PostMapping("")
    fun updateBookPublishers(
        @PathVariable
        bookId: Long,
        @RequestBody
        publishers: List<Publisher>,
        @RequestParam(required = false, name = "deleteExisting")
        delete: Boolean = false
    ): ResponseEntity<BookDTO> {
        val book = bookIntermediatesServices.updatePublishers(bookId, publishers, delete)
        return ResponseEntity.ok(BookDTO(book))
    }
}