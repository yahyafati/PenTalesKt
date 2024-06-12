package org.pentales.pentalesrest.models.entities.book.genre.bookGenre

import org.pentales.pentalesrest.models.entities.book.*
import org.pentales.pentalesrest.models.entities.book.dto.*
import org.pentales.pentalesrest.models.entities.book.genre.*
import org.pentales.pentalesrest.utils.*
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
        return ResponseEntity.ok(BookDTO(book, ServletUtil.getBaseURLFromCurrentRequest()))
    }
}