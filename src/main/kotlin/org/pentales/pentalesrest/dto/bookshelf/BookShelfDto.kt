package org.pentales.pentalesrest.dto.bookshelf

import org.pentales.pentalesrest.dto.book.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*

data class BookShelfDto(
    var title: String = "",
    var description: String = "",
    var access: EAccessLevel = EAccessLevel.PRIVATE,
    var books: List<BookDTO> = listOf(),
    var owner: UserDto = UserDto(),
    var readLater: Boolean = false,
) {

    constructor(bookShelf: BookShelf) : this(
        title = bookShelf.title,
        description = bookShelf.description,
        access = bookShelf.access,
        books = bookShelf.books.map { BookDTO(it.book) },
        owner = UserDto(bookShelf.owner),
        readLater = bookShelf.readLater,
    )
}
