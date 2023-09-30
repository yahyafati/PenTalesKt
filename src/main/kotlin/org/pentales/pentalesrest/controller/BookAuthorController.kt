package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book/{bookId}/authors")
class BookAuthorController(private val bookIntermediatesServices: IBookIntermediatesServices) {

    val service: IBookIntermediatesServices
        get() = bookIntermediatesServices

    @PostMapping("")
    fun updateBookAuthors(
        @PathVariable bookId: Long,
        @RequestBody authors: List<Author>,
        @RequestParam(required = false, name = "deleteExisting") delete: Boolean = false
    ): ResponseEntity<BookDTO> {
        val book = bookIntermediatesServices.updateAuthors(bookId, authors, delete)
        return ResponseEntity.ok(BookDTO(book))
    }
}