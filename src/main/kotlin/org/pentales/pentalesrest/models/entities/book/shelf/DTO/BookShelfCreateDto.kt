package org.pentales.pentalesrest.models.entities.book.shelf.DTO

import org.pentales.pentalesrest.models.entities.book.shelf.*
import org.pentales.pentalesrest.models.entities.user.*

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
