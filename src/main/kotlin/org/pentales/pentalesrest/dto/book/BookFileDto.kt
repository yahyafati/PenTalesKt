package org.pentales.pentalesrest.dto.book

import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*

data class BookFileDto(
    var id: Long = 0L,
    var path: String = "",
    var owner: UserDto = UserDto(),
    var book: BookDTO = BookDTO()
) {

    constructor(bookFile: BookFile, baseURL: String) : this(
        bookFile.id,
        UserDto.getURLWithBaseURL(bookFile.path, baseURL) ?: "",
        UserDto(bookFile.owner, baseURL),
        BookDTO(bookFile.book)
    )
}