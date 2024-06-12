package org.pentales.pentalesrest.models.entities.book.shelf.DTO

import org.pentales.pentalesrest.models.entities.book.dto.*
import org.pentales.pentalesrest.models.entities.book.shelf.*
import org.pentales.pentalesrest.models.entities.user.dto.*

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
        books = bookShelf.books.map { ActivityBookDto(it.book, baseURL) },
        owner = UserDto(bookShelf.owner, baseURL),
        readLater = bookShelf.readLater,
        shelfType = if (bookShelf.readLater) EShelfType.READ_LATER else EShelfType.NORMAL
    )
}
