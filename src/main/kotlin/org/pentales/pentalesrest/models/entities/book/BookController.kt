package org.pentales.pentalesrest.models.entities.book

import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.global.dto.*
import org.pentales.pentalesrest.global.dto.file.*
import org.pentales.pentalesrest.models.entities.activity.book.*
import org.pentales.pentalesrest.models.entities.book.dto.*
import org.pentales.pentalesrest.models.entities.book.shelf.*
import org.pentales.pentalesrest.models.entities.book.userBook.status.*
import org.pentales.pentalesrest.models.entities.book.userBook.status.DTO.*
import org.pentales.pentalesrest.models.entities.rating.dto.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.*

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
        @RequestBody(required = false)
        filters: List<FilterDto>? = listOf()
    ): ResponseEntity<Page<BookDTO>> {
        val pageRequest = ServletUtil.getPageRequest()
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        val response = service.findAll(pageRequest, filters ?: listOf()).map { BookDTO(it, baseURL) }
        return ResponseEntity.ok(response)
    }

    @GetMapping("/now-reading")
    fun getNowReadingBook(
        @RequestParam(required = false)
        username: String?,
    ): ResponseEntity<BasicResponseDto<NowReadingDto?>> {
        val currentUsername = authenticationFacade.forcedCurrentUser.username
        val userBookStatus = userBookStatusServices.getNowReadingBook(username ?: currentUsername)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        val dto = userBookStatus?.book?.let {
            NowReadingDto(
                book = BookDTO(it, baseURL),
                startedAt = userBookStatus.createdAt
            )
        }
        return ResponseEntity.ok(BasicResponseDto.ok(dto))
    }

    @GetMapping("/{id}")
    fun getBookById(
        @PathVariable
        id: Long,
    ): ResponseEntity<BasicResponseDto<BookDTO>> {
        val book = service.findById(id)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(BasicResponseDto.ok(BookDTO(book, baseURL)))
    }

    @GetMapping("/random")
    fun getRandomBook(): ResponseEntity<BasicResponseDto<ActivityBookDto>> {
        val book = service.getRandomBook()
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        val dto = ActivityBookDto(ActivityBook(book), baseURL)
        return ResponseEntity.ok(BasicResponseDto.ok(dto))
    }

    @GetMapping("/{id}/rating-info")
    fun getBookRatingInfoById(
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
    ): ResponseEntity<BasicResponseDto<Page<RatingDto>>> {
        val pageRequest = ServletUtil.getPageRequest()
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
    ): ResponseEntity<BasicResponseDto<Page<BookDTO>>> {
        val pageRequest = ServletUtil.getPageRequest()
        val books = bookServices.getRelatedBooks(bookId, pageRequest)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(BasicResponseDto.ok(books.map { BookDTO(it, baseURL) }))
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
    ): ResponseEntity<BasicResponseDto<BookDTO>> {
        val book = bookDTO.toBook()
        val savedBook = service.saveNew(book)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(BasicResponseDto.ok(BookDTO(savedBook, baseURL)))
    }

    @PostMapping(
        "/{bookId}/upload-cover",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadBookCover(
        @PathVariable
        bookId: Long,
        @RequestBody
        file: MultipartFile
    ): ResponseEntity<BasicResponseDto<BookDTO>> {

        val imageUploadDto = ImageUploadDto(file = file)
        val book = service.uploadBookCover(bookId, imageUploadDto)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(
            BasicResponseDto.ok(BookDTO(book, baseURL))
        )
    }

    @PutMapping("/{id}")
    fun updateBook(
        @PathVariable
        id: Long,
        @RequestBody
        bookDTO: BookDTO,
        @RequestParam(required = false)
        includeFields: List<String>?
    ): ResponseEntity<BasicResponseDto<BookDTO>> {
        val book = bookDTO.toBook()
        val updatedBook = service.update(id, book, includeFields ?: emptyList())
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(BasicResponseDto.ok(BookDTO(updatedBook, baseURL)))
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
