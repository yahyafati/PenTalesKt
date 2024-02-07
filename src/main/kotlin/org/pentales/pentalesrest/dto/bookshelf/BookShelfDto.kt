package org.pentales.pentalesrest.dto.bookshelf

import org.pentales.pentalesrest.dto.book.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*

data class BookShelfDto(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var access: EAccessLevel = EAccessLevel.PRIVATE,
    var books: List<ActivityBookDto> = listOf(),
    var owner: UserDto = UserDto(),
    var readLater: Boolean = false,
    val shelfType: EShelfType = EShelfType.NORMAL,
    var bookAdded: Boolean? = null,
) {

    constructor(bookShelf: BookShelf, baseURL: String) : this(
        id = bookShelf.id,
        title = bookShelf.title,
        description = bookShelf.description,
        access = bookShelf.access,
        books = bookShelf.books.map { ActivityBookDto(it.book) },
        owner = UserDto(bookShelf.owner, baseURL),
        readLater = bookShelf.readLater,
        shelfType = if (bookShelf.readLater) EShelfType.READ_LATER else EShelfType.NORMAL
    )
}
