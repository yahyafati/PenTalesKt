package org.pentales.pentalesrest.controller.rest.book

import org.pentales.pentalesrest.controller.rest.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.book.*
import org.pentales.pentalesrest.dto.rating.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book")
class BookController(
    private val bookServices: IBookServices,
    private val userBookStatusServices: IUserBookStatusServices,
    private val authenticationFacade: IAuthenticationFacade,
    private val bookShelfServices: IBookShelfServices,
) {

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

    @GetMapping("/now-reading")
    fun getNowReadingBook(): ResponseEntity<BasicResponseDto<NowReadingDto?>> {
        val userId = authenticationFacade.forcedCurrentUser.id
        val userBookStatus = userBookStatusServices.getNowReadingBook(userId)
        val dto = userBookStatus?.book?.let { NowReadingDto(book = BookDTO(it), startedAt = userBookStatus.createdAt) }
        return ResponseEntity.ok(BasicResponseDto.ok(dto))
    }

    @GetMapping("/{id}")
    fun getBookById(
        @PathVariable
        id: Long,
    ): ResponseEntity<BasicResponseDto<BookDTO>> {
        val book = service.findById(id)
        return ResponseEntity.ok(BasicResponseDto.ok(BookDTO(book)))
    }

    @GetMapping("/{id}/rating-info")
    fun getBookRatingById(
        @PathVariable
        id: Long,
    ): ResponseEntity<BasicResponseDto<BookRatingInfoDto>> {
        val rating = bookServices.getBookRatingInfo(id)
        return ResponseEntity.ok(BasicResponseDto.ok(BookRatingInfoDto(rating)))
    }

    @GetMapping("/{bookId}/rating/user")
    fun getBookRatingByUserId(
        @PathVariable
        bookId: Long,
        @RequestParam(required = false)
        userId: Long?,
    ): ResponseEntity<BasicResponseDto<RatingDto?>> {
        val currentUserId = authenticationFacade.forcedCurrentUser.id
        val rating = bookServices.getBookRatingByUser(bookId = bookId, userId = userId ?: currentUserId)
        return ResponseEntity.ok(BasicResponseDto.ok(rating?.let {
            RatingDto(
                it,
                ServletUtil.getBaseURLFromCurrentRequest()
            )
        }))
    }

    @GetMapping("/{id}/rating")
    fun getBookRatingById(
        @PathVariable
        id: Long,
        @RequestParam(defaultValue = "0")
        page: Int?,
        @RequestParam(defaultValue = "10")
        size: Int?,
        @RequestParam(defaultValue = "")
        sort: String?,
        @RequestParam(defaultValue = "ASC")
        direction: Sort.Direction?,
    ): ResponseEntity<BasicResponseDto<Page<RatingDto>>> {
        val pageRequest = IBasicControllerSkeleton.getPageRequest(
            page, size, sort, direction
        )
        val ratings = bookServices.getBookRatings(id, pageRequest)
        return ResponseEntity.ok(BasicResponseDto.ok(ratings.map {
            RatingDto(
                it,
                ServletUtil.getBaseURLFromCurrentRequest()
            )
        }))
    }

    @GetMapping("/{bookId}/related")
    fun getRelatedBooks(
        @PathVariable
        bookId: Long,
        @RequestParam(defaultValue = "0")
        page: Int?,
        @RequestParam(defaultValue = "10")
        size: Int?,
        @RequestParam(defaultValue = "")
        sort: String?,
        @RequestParam(defaultValue = "ASC")
        direction: Sort.Direction?,
    ): ResponseEntity<BasicResponseDto<Page<BookDTO>>> {
        val pageRequest = IBasicControllerSkeleton.getPageRequest(
            page, size, sort, direction
        )
        val books = bookServices.getRelatedBooks(bookId, pageRequest)
        return ResponseEntity.ok(BasicResponseDto.ok(books.map { BookDTO(it) }))
    }

    @GetMapping("/{bookId}/status")
    fun getBookStatusById(
        @PathVariable
        bookId: Long
    ): ResponseEntity<BasicResponseDto<UserBookStatusDto>> {
        val userId = authenticationFacade.forcedCurrentUser.id
        val status = userBookStatusServices.getBookStatus(userId = userId, bookId = bookId)

        return ResponseEntity.ok().body(BasicResponseDto.ok(UserBookStatusDto(bookId = bookId, status = status)))
    }

    @PatchMapping("/{bookId}/status")
    fun updateBookStatusById(
        @PathVariable
        bookId: Long,
        @RequestBody
        statusString: String
    ): ResponseEntity<BasicResponseDto<UserBookStatusDto>> {
        val userId = authenticationFacade.forcedCurrentUser.id
        val status = EUserBookReadStatus.valueOf(statusString)
        val userBookStatus = userBookStatusServices.updateBookStatus(userId, bookId, status)
        return ResponseEntity.ok(BasicResponseDto.ok(UserBookStatusDto(userBookStatus)))
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

    @PostMapping("/{id}/add-to-shelf")
    fun addToShelf(
        @PathVariable
        id: Long,
        @RequestBody
        list: Set<Long>
    ): ResponseEntity<Unit> {
        val user = authenticationFacade.forcedCurrentUser
        bookShelfServices.addBookToShelves(user, Book(id = id), list.map {
            val shelf = BookShelf()
            shelf.id = it
            shelf
        }, true)
        return ResponseEntity.ok().build()
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
