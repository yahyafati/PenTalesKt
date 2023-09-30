package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.services.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book")
class BookController(private val bookServices: IBookServices) {

    val service: IBookServices
        get() = bookServices

    @get:GetMapping("")
    val all: ResponseEntity<List<BookDTO>>
        get() = ResponseEntity.ok(service.findAll().map { BookDTO(it) })

    @GetMapping("/{id}")
    fun getBookById(
        @PathVariable id: Long
    ): ResponseEntity<BookDTO> {
        val book = service.findById(id)
        return ResponseEntity.ok(BookDTO(book))
    }

    @PostMapping("")
    fun saveBook(
        @RequestBody bookDTO: BookDTO
    ): ResponseEntity<BookDTO> {
        val book = bookDTO.toBook()
        val savedBook = service.saveNew(book)
        return ResponseEntity.ok(BookDTO(savedBook))
    }

    @PutMapping("/{id}")
    fun updateBook(
        @PathVariable id: Long, @RequestBody bookDTO: BookDTO
    ): ResponseEntity<BookDTO> {
        val book = bookDTO.toBook()
        val updatedBook = service.update(id, book)
        return ResponseEntity.ok(BookDTO(updatedBook))
    }

    @DeleteMapping("/{id}")
    fun deleteBook(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        service.deleteById(id)
        return ResponseEntity.ok().build()
    }
}
