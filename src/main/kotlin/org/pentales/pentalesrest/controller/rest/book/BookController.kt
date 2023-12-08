package org.pentales.pentalesrest.controller.rest.book

import org.pentales.pentalesrest.controller.rest.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book")
class BookController(private val bookServices: IBookServices) {

    val service: IBookServices
        get() = bookServices

    @PostMapping("/search")
    fun searchBooks(
        @RequestParam(defaultValue = "0")
        page: Int?,
        @RequestParam(defaultValue = "10")
        size: Int?,
        @RequestParam(defaultValue = "")
        sort: String?,
        @RequestParam(defaultValue = "ASC")
        direction: Sort.Direction?,
        @RequestBody(required = false)
        filters: List<FilterDto>? = listOf()
    ): ResponseEntity<Page<BookDTO>> {
        val pageRequest = IBasicControllerSkeleton.getPageRequest(
            page, size, sort, direction
        )
        val response = service.findAll(pageRequest, filters ?: listOf()).map { BookDTO(it) }
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getBookById(
        @PathVariable
        id: Long
    ): ResponseEntity<BookDTO> {
        val book = service.findById(id)
        return ResponseEntity.ok(BookDTO(book))
    }

    @PostMapping("")
    fun saveBook(
        @RequestBody
        bookDTO: BookDTO
    ): ResponseEntity<BookDTO> {
        val book = bookDTO.toBook()
        val savedBook = service.saveNew(book)
        return ResponseEntity.ok(BookDTO(savedBook))
    }

    @PutMapping("/{id}")
    fun updateBook(
        @PathVariable
        id: Long,
        @RequestBody
        bookDTO: BookDTO,
        @RequestParam(required = false)
        includeFields: List<String>?
    ): ResponseEntity<BookDTO> {
        val book = bookDTO.toBook()
        val updatedBook = service.update(id, book, includeFields ?: emptyList())
        return ResponseEntity.ok(BookDTO(updatedBook))
    }

    @DeleteMapping("/{id}")
    fun deleteBook(
        @PathVariable
        id: Long
    ): ResponseEntity<Unit> {
        service.deleteById(id)
        return ResponseEntity.ok().build()
    }
}
