package org.pentales.pentalesrest.controller.book

import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.book.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.*

@RestController
@RequestMapping("/api/book-file")
class BookFileController(
    private val bookServices: org.pentales.pentalesrest.services.IBookServices,
    private val authenticationFacade: IAuthenticationFacade,
) {

    @PostMapping(
        "{bookId}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadEbook(
        @PathVariable
        bookId: Long,
        @RequestPart("file")
        file: MultipartFile,
    ): ResponseEntity<BasicResponseDto<BookFileDto>> {
        val book = bookServices.uploadEbook(file, bookId)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(BasicResponseDto.ok(BookFileDto(book, baseURL)))
    }

    @GetMapping
    fun getEbooks(
        @RequestParam
        bookId: Long
    ): ResponseEntity<BasicResponseDto<Page<BookFileDto>>> {
        val user = authenticationFacade.forcedCurrentUser
        val pageParams = ServletUtil.getPageParamsFromCurrentRequest()
        pageParams.sort = Sort.by(Sort.Direction.DESC, "updatedAt")
        val pageRequest = ServletUtil.getPageRequest(pageParams)
        val bookFile = bookServices.getUserEbooks(user, Book(id = bookId), pageRequest)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(BasicResponseDto.ok(bookFile.map { BookFileDto(it, baseURL) }))
    }

    @GetMapping("/{fileId}")
    fun getEbook(
        @PathVariable
        fileId: Long,
    ): ResponseEntity<BasicResponseDto<BookFileDto>> {
        val bookFile = bookServices.getEbook(fileId)
        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        return ResponseEntity.ok(BasicResponseDto.ok(BookFileDto(bookFile, baseURL)))
    }

    @PostMapping("/{fileId}/progress")
    fun updateEbookProgress(
        @PathVariable
        fileId: Long,
        @RequestBody
        updateBookProgressDto: UpdateBookProgressDto,
    ): ResponseEntity<BasicResponseDto<Unit>> {
        val user = authenticationFacade.forcedCurrentUser
        bookServices.updateEbookProgress(user, BookFile(id = fileId), updateBookProgressDto.progress)
        return ResponseEntity.ok(BasicResponseDto.ok(Unit))
    }

    @GetMapping("/{bookId}/exists")
    fun getEbookExists(
        @PathVariable
        bookId: Long,
    ): ResponseEntity<BasicResponseDto<Boolean>> {
        val user = authenticationFacade.forcedCurrentUser
        val bookFile = bookServices.existsUserEbook(user, Book(id = bookId))
        return ResponseEntity.ok(BasicResponseDto.ok(bookFile))
    }

    @DeleteMapping("/{fileId}")
    fun deleteEbook(
        @PathVariable
        fileId: Long,
    ): ResponseEntity<BasicResponseDto<Unit>> {
        val user = authenticationFacade.forcedCurrentUser
        bookServices.deleteEbook(fileId, user)
        return ResponseEntity.ok(BasicResponseDto.ok(Unit))
    }
}