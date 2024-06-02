package org.pentales.pentalesrest.dto.book

import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*

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