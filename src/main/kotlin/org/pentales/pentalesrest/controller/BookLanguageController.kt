package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book/{bookId}/languages")
class BookLanguageController(private val bookIntermediatesServices: IBookIntermediatesServices) {

    val service: IBookIntermediatesServices
        get() = bookIntermediatesServices

    @PostMapping("")
    fun updateBookLanguages(
        @PathVariable bookId: Long,
        @RequestBody languages: List<Language>,
        @RequestParam(required = false, name = "deleteExisting") delete: Boolean = false
    ): ResponseEntity<BookDTO> {
        val book = bookIntermediatesServices.updateLanguages(bookId, languages, delete)
        return ResponseEntity.ok(BookDTO(book))
    }
}