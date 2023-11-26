package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.services.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book")
class BookPageController(private val bookPageServices: IBookPageServices) {

    @GetMapping("/{id}")
    fun getBookById(
        @PathVariable
        id: Long
    ): ResponseEntity<Map<String, Any>> {
        val bookData = bookPageServices.getBookPageData(id)

        return ResponseEntity.ok(bookData)
    }

}