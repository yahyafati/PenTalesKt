package org.pentales.pentalesrest.models.entities.book.dto

import org.pentales.pentalesrest.models.entities.book.file.*
import org.pentales.pentalesrest.models.entities.user.dto.*

data class BookFileDto(
    var id: Long = 0L,
    var path: String = "",
    var owner: UserDto = UserDto(),
    var book: BookDTO = BookDTO(),
    var progress: String = "",
    var isPublic: Boolean = false,
    var lastRead: Long? = null
) {

    constructor(bookFile: BookFile, baseURL: String) : this(
        id = bookFile.id,
        path = UserDto.getURLWithBaseURL(bookFile.path, baseURL) ?: "",
        owner = UserDto(bookFile.owner, baseURL),
        book = BookDTO(bookFile.book, baseURL),
        progress = bookFile.__progress,
        isPublic = bookFile.isPublic,
        lastRead = bookFile.__lastRead?.time
    )
}