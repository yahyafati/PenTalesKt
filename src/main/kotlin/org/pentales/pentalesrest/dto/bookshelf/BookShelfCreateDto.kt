package org.pentales.pentalesrest.dto.bookshelf

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*

data class BookShelfCreateDto(
    var title: String = "",
    var description: String = "",
    var access: EAccessLevel = EAccessLevel.PRIVATE,
) {

    constructor(bookShelf: BookShelf) : this(
        title = bookShelf.title,
        description = bookShelf.description,
        access = bookShelf.access,
    )

    fun toModel(owner: User): BookShelf {
        return BookShelf(
            title = title,
            description = description,
            access = access,
            owner = owner,
        )
    }
}
