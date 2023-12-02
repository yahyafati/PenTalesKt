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

    @GetMapping("")
    fun getAll(
        @RequestParam(defaultValue = "0")
        page: Int?,
        @RequestParam(defaultValue = "10")
        size: Int?,
        @RequestParam(defaultValue = "")
        sort: String?,
        @RequestParam(defaultValue = "ASC")
        direction: Sort.Direction?,
        @RequestBody
        filters: List<FilterDto> = listOf()
    ): ResponseEntity<Page<BookDTO>> {
        val pageNumber = page ?: 0
        val pageSize = size ?: 10
        val sortDirection = direction ?: Sort.Direction.ASC

        val pageRequest = if (sort.isNullOrEmpty()) {
            PageRequest.of(pageNumber, pageSize.coerceAtMost(IBasicControllerSkeleton.MAX_PAGE_SIZE))
        } else {
            PageRequest.of(
                pageNumber, pageSize.coerceAtMost(IBasicControllerSkeleton.MAX_PAGE_SIZE), Sort.by(sortDirection, sort)
            )
        }
        return ResponseEntity.ok(service.findAll(pageRequest, filters).map { BookDTO(it) })
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
