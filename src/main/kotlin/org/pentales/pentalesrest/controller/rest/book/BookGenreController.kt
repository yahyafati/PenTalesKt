package org.pentales.pentalesrest.controller.rest.book

import org.pentales.pentalesrest.dto.book.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book/{bookId}/genres")
class BookGenreController(private val bookIntermediatesServices: IBookIntermediatesServices) {

    val service: IBookIntermediatesServices
        get() = bookIntermediatesServices

    @PostMapping("")
    fun updateBookGenres(
        @PathVariable
        bookId: Long,
        @RequestBody
        genres: List<Genre>,
        @RequestParam(required = false, name = "deleteExisting")
        delete: Boolean = false
    ): ResponseEntity<BookDTO> {
        val book = bookIntermediatesServices.updateGenres(bookId, genres, delete)
        return ResponseEntity.ok(BookDTO(book))
    }
}