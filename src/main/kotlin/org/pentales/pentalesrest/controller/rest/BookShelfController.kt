package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.bookshelf.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.*

@RestController
@RequestMapping("/api/bookshelf")
class BookShelfController(
    private val bookShelfServices: IBookShelfServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    fun validateAccess(bookShelf: BookShelf, requester: User) {
        if (bookShelf.access == EAccessLevel.PRIVATE) {
            if (bookShelf.owner.id != requester.id) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "BookShelf not found or not accessible")
            }
        }
        // TODO check for FRIENDS access level
    }

    @GetMapping
    fun getAll(
        @RequestParam(required = false)
        username: String?,
        @RequestParam(required = false)
        page: Int?,
        @RequestParam(required = false)
        size: Int?,
        @RequestParam(required = false)
        sort: String?,
    ): ResponseEntity<BasicResponseDto<Page<BookShelfDto>>> {
        val currentUsername = authenticationFacade.forcedCurrentUser.username
        val pageable = PageRequest.of(page ?: 0, size ?: 10, Sort.by(sort ?: "id"))
        val response =
            bookShelfServices.findAllByOwnerUsername(username ?: currentUsername, pageable).map { BookShelfDto(it) }
        return ResponseEntity.ok(BasicResponseDto.ok(response))
    }

    @GetMapping("/{id}")
    fun getOne(
        @PathVariable
        id: Long
    ): ResponseEntity<BasicResponseDto<BookShelfDto>> {
        val currentUser = authenticationFacade.forcedCurrentUser
        val bookShelf = bookShelfServices.findById(id)
        validateAccess(bookShelf, currentUser)
        val dto = BookShelfDto(bookShelf)
        return ResponseEntity.ok(BasicResponseDto.ok(dto))
    }

    @GetMapping("/read-later")
    fun getReadLater(
        @RequestParam(required = false)
        username: String?,
        @RequestParam(required = false)
        page: Int?,
        @RequestParam(required = false)
        size: Int?,
        @RequestParam(required = false)
        sort: String?,
    ): ResponseEntity<BasicResponseDto<BookShelfDto>> {
        val currentUser = authenticationFacade.forcedCurrentUser
        val bookShelf = bookShelfServices.findReadLater(username ?: currentUser.username)
        validateAccess(bookShelf, currentUser)
        val dto = BookShelfDto(bookShelf)
        return ResponseEntity.ok(BasicResponseDto.ok(dto))
    }
}