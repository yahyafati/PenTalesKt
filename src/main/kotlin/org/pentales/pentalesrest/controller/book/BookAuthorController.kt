package org.pentales.pentalesrest.controller.book

import org.pentales.pentalesrest.dto.book.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.utils.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book/{bookId}/authors")
class BookAuthorController(private val bookIntermediatesServices: org.pentales.pentalesrest.services.IBookIntermediatesServices) {

    val service: org.pentales.pentalesrest.services.IBookIntermediatesServices
        get() = bookIntermediatesServices

    @PostMapping("")
    fun updateBookAuthors(
        @PathVariable
        bookId: Long,
        @RequestBody
        authors: List<Author>,
        @RequestParam(required = false, name = "deleteExisting")
        delete: Boolean = false
    ): ResponseEntity<BookDTO> {
        val book = bookIntermediatesServices.updateAuthors(bookId, authors, delete)
        return ResponseEntity.ok(BookDTO(book, ServletUtil.getBaseURLFromCurrentRequest()))
    }
}